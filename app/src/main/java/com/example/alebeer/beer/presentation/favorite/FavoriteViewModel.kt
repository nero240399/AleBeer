package com.example.alebeer.beer.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alebeer.beer.data.mapper.toBeer
import com.example.alebeer.beer.domain.repository.BeerRepository
import com.example.alebeer.beer.domain.repository.InternalImageRepository
import com.example.alebeer.beer.presentation.component.BearInfoUiState
import com.example.alebeer.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val beerRepository: BeerRepository,
    private val imageRepository: InternalImageRepository
) : ViewModel() {

    private val _savedListBeer = beerRepository.getBeerStream()
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<BearInfoUiState> = combine(
        _savedListBeer,
        _isLoading
    ) { listBeerEntity, isLoading ->
        val listImage = imageRepository.loadImages()
        val listBeer = listBeerEntity.map { it.toBeer(listImage) }
        BearInfoUiState(listBeer, isLoading)
    }.stateIn(viewModelScope, WhileUiSubscribed, BearInfoUiState())

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.OnUpdateButton -> viewModelScope.launch {
                beerRepository.updateBeerInfo(
                    event.beer,
                    event.note
                )
            }
            is FavoriteEvent.OnDeleteButton -> viewModelScope.launch {
                beerRepository.deleteBeerInfo(
                    event.beer
                )
                launch { imageRepository.deleteImage("${event.beer.name}.png") }
            }
        }
    }
}
