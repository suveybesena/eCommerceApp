package com.example.capstoneproject.presentation.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Basket
import com.example.capstoneproject.data.entities.product.Purchased
import com.example.capstoneproject.domain.usecase.local.product.DeleteBasketProductUseCase
import com.example.capstoneproject.domain.usecase.local.product.GetBasketItemsCountUseCase
import com.example.capstoneproject.domain.usecase.local.product.GetBasketProductsFromDatabaseUseCase
import com.example.capstoneproject.domain.usecase.local.product.InsertProductToPurchasedUseCase
import com.example.capstoneproject.domain.usecase.remote.product.DeleteProductFromBagUseCase
import com.example.capstoneproject.domain.usecase.remote.product.GetBagProductsByUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketProductsFromDatabaseUseCase: GetBasketProductsFromDatabaseUseCase,
    private val insertProductToPurchasedUseCase: InsertProductToPurchasedUseCase,
    private val deleteBasketProductUseCase: DeleteBasketProductUseCase,
    private val getBasketItemCount: GetBasketItemsCountUseCase,
    private val getBagProductsByUserUseCase: GetBagProductsByUserUseCase,
    private val deleteProductFromBagUseCase: DeleteProductFromBagUseCase
) :
    ViewModel() {

    private val uiState = MutableStateFlow(BasketUiState())
    val _uiState: StateFlow<BasketUiState> = uiState.asStateFlow()

    fun handleEvent(event: BasketUiEvent) {
        when (event) {
            is BasketUiEvent.GetAllBasketItems -> {
                getAllBasketItems(event.userId)
            }
            is BasketUiEvent.InsertPurchasedToDatabase -> {
                insertPurchased(event.purchased)
            }
            is BasketUiEvent.DeleteBasketItem -> {
                deleteProduct(event.basketItem)
            }
            is BasketUiEvent.GetBasketItemCount -> {
                getBasketItemsCount(event.userId)
            }
            is BasketUiEvent.GetBagBasketFromAPI -> {
                getBagProducts(event.userId)
            }
            is BasketUiEvent.DeleteProductFromBag->{
                deleteBagProduct(event.id)
            }
        }
    }

    private fun deleteBagProduct(id: Int) {
        viewModelScope.launch {
            deleteProductFromBagUseCase.invoke(id).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(deleteBagResponse = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun getBagProducts(userId: String) {
        viewModelScope.launch {
            getBagProductsByUserUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(bagProducts = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun getBasketItemsCount(userId: String) {
        viewModelScope.launch {
            getBasketItemCount.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(basketItemsCount = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun deleteProduct(basketItem: Basket) {
        viewModelScope.launch {
            deleteBasketProductUseCase.invoke(basketItem).collect { resultState ->
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

    private fun insertPurchased(purchased: Purchased) {
        viewModelScope.launch {
            insertProductToPurchasedUseCase.invoke(purchased).collect { resultState ->
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