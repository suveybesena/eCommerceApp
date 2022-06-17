package com.example.capstoneproject.presentation.favorites.mycollection

import com.example.capstoneproject.data.entities.product.Collection

sealed class MyCollectionUiEvent {
    data class GetAllCollections(val userId: String) : MyCollectionUiEvent()
    object GetAllProducts : MyCollectionUiEvent()
    data class DeleteProduct(val collection: Collection) : MyCollectionUiEvent()
    data class GetNewProducts(val categoryName: String) : MyCollectionUiEvent()
}
