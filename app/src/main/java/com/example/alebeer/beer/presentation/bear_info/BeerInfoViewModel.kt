package com.example.alebeer.beer.presentation.bearinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alebeer.beer.data.mapper.toBeer
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.beer.domain.repository.BeerRepository
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
    private val repository: BeerRepository
) : ViewModel() {

    private val _savedListBeer = repository.getBeerStream()
    private val _listBeer = MutableStateFlow(listOf<Beer>())
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage = MutableStateFlow("")

    private val _listUiBeer =
        combine(_listBeer, _savedListBeer) { listBeerDto, listBeerEntity ->
            val listBeer = listBeerDto.map { beerDto ->
                listBeerEntity.forEach {
                    if (beerDto.id == it.id) {
                        return@map Beer(
                            it.id,
                            it.name,
                            it.price,
                            it.note,
                            beerDto.imageUrl,
                            isSaved = true
                        )
                    }
                }
                Beer(beerDto.id, beerDto.name, beerDto.price, "", beerDto.imageUrl)
            }
            listBeer
        }

    val uiState: StateFlow<BearInfoUiState> = combine(
        _listUiBeer,
        _isLoading,
        _userMessage
    ) { listBeer, isLoading, message ->
        BearInfoUiState(listBeer, isLoading, message)
    }.stateIn(viewModelScope, WhileUiSubscribed, BearInfoUiState())

    init {
        viewModelScope.launch {
            _isLoading.value = true
            when (val listBeer = repository.fetchBeerInfo()) {
                is Result.Success -> _listBeer.update { listBeer.data.map { it.toBeer() } }
                is Result.Error -> showSnackbar(listBeer)
            }
            _isLoading.value = false
        }
    }

    fun onEvent(event: BeerInfoEvent) {
        when (event) {
            is BeerInfoEvent.OnSaveButton -> viewModelScope.launch {
                repository.saveBeerInfo(event.beer, event.bitmap, event.note)
            }
            else -> Unit
        }
    }

    fun snackbarShown() {
        _userMessage.value = ""
    }

    private fun showSnackbar(error: Result.Error) {
        _userMessage.value = error.exception.message ?: "Something went wrong"
    }
}
