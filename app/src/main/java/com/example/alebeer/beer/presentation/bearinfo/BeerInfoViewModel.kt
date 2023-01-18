package com.example.alebeer.beer.presentation.bearinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alebeer.beer.data.mapper.toBeer
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.beer.domain.repository.BeerRepository
import com.example.alebeer.beer.domain.repository.InternalImageRepository
import com.example.alebeer.beer.presentation.component.BearInfoUiState
import com.example.alebeer.util.Result
import com.example.alebeer.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerInfoViewModel @Inject constructor(
    private val beerRepository: BeerRepository,
    private val imageRepository: InternalImageRepository
) : ViewModel() {

    private val _savedListBeer = beerRepository.getBeerStream()
    private val _listBeer = MutableStateFlow(listOf<Beer>())
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage = MutableStateFlow("")

    val uiState: StateFlow<BearInfoUiState> = combine(
        _listBeer,
        _savedListBeer,
        _isLoading,
        _userMessage
    ) { listBeer, listBeerEntity, isLoading, message ->
        val listUiBeer = listBeer.map { beer ->
            listBeerEntity.forEach {
                if (beer.id == it.id) {
                    return@map it.toBeer(beer.imageUrl)
                }
            }
            beer
        }
        BearInfoUiState(listUiBeer, isLoading, message)
    }.stateIn(viewModelScope, WhileUiSubscribed, BearInfoUiState())

    init {
        viewModelScope.launch {
            _isLoading.value = true
            when (val listBeer = beerRepository.fetchBeerInfo()) {
                is Result.Success -> _listBeer.update { listBeer.data.map { it.toBeer() } }
                is Result.Error -> showSnackbar(listBeer)
            }
            _isLoading.value = false
        }
    }

    fun onEvent(event: BeerInfoEvent) {
        when (event) {
            is BeerInfoEvent.OnSaveButton -> viewModelScope.launch {
                val list = _listBeer.value.toMutableList()
                _listBeer.update { list.also { it[event.position].isSaving = true } }
                beerRepository.saveBeerInfo(event.beer, event.note)
                _listBeer.update { list.also { it[event.position].isSaving = false } }
                launch { imageRepository.saveImage(event.beer.name, event.bitmap) }
            }
        }
    }

    fun snackbarShown() {
        _userMessage.value = ""
    }

    private fun showSnackbar(error: Result.Error) {
        _userMessage.value = error.exception.message ?: "Something went wrong"
    }
}
