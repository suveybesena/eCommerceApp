package com.example.capstoneproject.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.extensions.Resource
import com.example.capstoneproject.data.model.product.Basket
import com.example.capstoneproject.domain.usecase.local.InsertProductToBasketUseCase
import com.example.capstoneproject.domain.usecase.remote.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val insertProductToBasketUseCase: InsertProductToBasketUseCase
) :
    ViewModel() {

    private val uiState = MutableStateFlow(DetailUiState())
    val _uiState: StateFlow<DetailUiState> = uiState.asStateFlow()

    fun handleEvent(event: DetailUiEvent) {
        when (event) {
            is DetailUiEvent.GetProductById -> {
                getProductById(event.productId)
            }
            is DetailUiEvent.InsertProductToBasket -> {
                insertProductToBasket(event.basketItem)
            }
        }
    }

    private fun insertProductToBasket(basketItem: Basket) {
        viewModelScope.launch {
            insertProductToBasketUseCase.invoke(basketItem).collect { resultState ->
                when (resultState) {
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                }
            }
        }
    }

    private fun getProductById(productId: Int) {
        viewModelScope.launch {
            getProductByIdUseCase.invoke(productId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(product = resultState.data)
                        }
                    }
                }
            }
        }
    }
}