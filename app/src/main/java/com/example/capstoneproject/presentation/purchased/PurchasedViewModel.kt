package com.example.capstoneproject.presentation.purchased

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.domain.usecase.local.product.GetPurchasedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasedViewModel @Inject constructor(private val getPurchasedProductsUseCase: GetPurchasedProductsUseCase) :
    ViewModel() {

    private val uiState = MutableStateFlow(PurchasedUiState())
    val _uiState: StateFlow<PurchasedUiState> = uiState.asStateFlow()

    fun handleEvent(event: PurchasedUiEvent) {
        when (event) {
            is PurchasedUiEvent.GetPurchasedProducts -> {
                getPurchasedProducts(event.userId)
            }
        }
    }

    private fun getPurchasedProducts(userId: String) {
        viewModelScope.launch {
            getPurchasedProductsUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(purchasedProducts = resultState.data)
                        }
                    }
                }
            }
        }
    }
}