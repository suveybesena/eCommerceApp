package com.example.capstoneproject.presentation.home

import com.example.capstoneproject.data.entities.product.Product


interface OnProductListToCollectionsClickHandler {
    fun addCollections(productModel: Product)
}