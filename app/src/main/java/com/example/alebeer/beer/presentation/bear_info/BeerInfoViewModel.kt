package com.example.alebeer.beer.presentation.bearinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alebeer.beer.data.remote.dto.BeerDto
import com.example.alebeer.beer.domain.repository.BeerRepository
import com.example.alebeer.util.Result
import com.example.alebeer.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerInfoViewModel @Inject constructor(
    private val repository: BeerRepository
) : ViewModel() {

    private val _listBeer = MutableStateFlow(listOf<BeerDto>())
    private val _isLoading = MutableStateFlow(false)
    private val _userMessage = MutableStateFlow("")

    val uiState = combine(_listBeer, _isLoading, _userMessage) { listBeer, isLoading, message ->
        BearInfoUiState(listBeer, isLoading, message)
    }.stateIn(viewModelScope, WhileUiSubscribed, BearInfoUiState())

    init {
        viewModelScope.launch {
            _isLoading.value = true
            when (val listBeer = repository.fetchBeerInfo()) {
                is Result.Success -> _listBeer.update { listBeer.data }
                is Result.Error -> showSnackbar(listBeer)
            }
            _isLoading.value = false
        }
    }

    fun snackbarShown() {
        _userMessage.value = ""
    }

    private fun showSnackbar(error: Result.Error) {
        _userMessage.value = error.exception.message ?: "Something went wrong"
    }
}
