package com.example.capstoneproject.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.domain.usecase.local.product.GetCollectionsItemsCountUseCase
import com.example.capstoneproject.domain.usecase.local.product.GetFavoritesItemsCountUseCase
import com.example.capstoneproject.domain.usecase.local.product.GetPurchasedItemsCountUseCase
import com.example.capstoneproject.domain.usecase.local.user.GetCurrentUserFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserFromDatabaseUseCase: GetCurrentUserFromDatabaseUseCase,
    private val getCollectionsItemsCountUseCase: GetCollectionsItemsCountUseCase,
    private val getFavoritesItemsCountUseCase: GetFavoritesItemsCountUseCase,
    private val getPurchasedItemsCountUseCase: GetPurchasedItemsCountUseCase
) :
    ViewModel() {

    private val uiState = MutableStateFlow(ProfileUiState())
    val _uiState: StateFlow<ProfileUiState> = uiState.asStateFlow()

    fun handleEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.GetCurrentUserId -> {
                getUserProducts(event.userId)
            }
            is ProfileUiEvent.GetCollectionItems -> {
                getCollectionsCount(event.userId)
            }
            is ProfileUiEvent.GetFavoritesItems -> {
                getFavoritesCount(event.userId)
            }
            is ProfileUiEvent.GetPurchasedItems -> {
                getPurchasedCount(event.userId)
            }
        }
    }

    private fun getPurchasedCount(userId: String) {
        viewModelScope.launch {
            getPurchasedItemsCountUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(purchasedCount = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun getFavoritesCount(userId: String) {
        viewModelScope.launch {
            getFavoritesItemsCountUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(favoritesCount = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun getCollectionsCount(userId: String) {
        viewModelScope.launch {
            getCollectionsItemsCountUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(collectionsCount = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun getUserProducts(userId: String) {
        viewModelScope.launch {
            getCurrentUserFromDatabaseUseCase.invoke(userId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(user = resultState.data)
                        }
                    }
                }
            }
        }
    }
}