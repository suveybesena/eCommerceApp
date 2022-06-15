package com.example.capstoneproject.presentation.favorites.mycollection

import com.example.capstoneproject.data.entities.product.Collection

interface OnCollectionsListToDeleteClickHandler {
    fun deleteProduct(collection: Collection)
}