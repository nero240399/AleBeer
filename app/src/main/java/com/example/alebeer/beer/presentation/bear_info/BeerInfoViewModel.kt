package com.example.alebeer.beer.presentation.bearinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alebeer.R
import com.example.alebeer.beer.data.local.entity.Beer
import com.example.alebeer.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerInfoViewModel @Inject constructor() : ViewModel() {

    private val _listBeer = MutableStateFlow(listOf<Beer>())
    private val _isLoading = MutableStateFlow(false)

    val uiState = combine(_listBeer, _isLoading) { listBeer, isLoading ->
        BearInfoUiState(listBeer, isLoading)
    }.stateIn(viewModelScope, WhileUiSubscribed, BearInfoUiState())

    init {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000)
            _listBeer.update {
                listOf(
                    Beer(R.drawable.ic_local_drink, "Tiger", 138, ""),
                    Beer(R.drawable.ic_local_drink, "Tiger", 138, ""),
                    Beer(R.drawable.ic_local_drink, "Tiger", 138, "")
                )
            }
            _isLoading.value = false
        }
    }
}
