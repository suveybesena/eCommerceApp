package com.example.capstoneproject.presentation.basket

import com.example.capstoneproject.data.entities.product.Basket

interface OnBasketListToDeleteClickHandler {
    fun deleteItem(basket: Basket)
}