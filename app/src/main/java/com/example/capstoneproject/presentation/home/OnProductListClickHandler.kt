package com.example.capstoneproject.presentation.home

import com.example.capstoneproject.data.entities.product.Product

interface OnProductListClickHandler {
    fun goDetailPage(productModel: Product)
}