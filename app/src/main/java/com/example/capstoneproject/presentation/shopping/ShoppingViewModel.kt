package com.example.capstoneproject.presentation.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.extensions.Resource
import com.example.capstoneproject.domain.usecase.local.GetBasketProductsFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val getBasketProductsFromDatabaseUseCase: GetBasketProductsFromDatabaseUseCase
) :
    ViewModel() {

    private val uiState = MutableStateFlow(ShoppingUiState())
    val _uiState: StateFlow<ShoppingUiState> = uiState.asStateFlow()

    fun handleEvent(event: ShoppingUiEvent) {
        when (event) {
            is ShoppingUiEvent.GetAllBasketItems -> {
                getAllBasketItems(event.userId)
            }
        }
    }

    private fun getAllBasketItems(userId: String) {
        viewModelScope.launch {
            getBasketProductsFromDatabaseUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(basketItems = resultState.data)
                        }
                    }
                }
            }
        }
    }
}