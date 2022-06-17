package com.example.capstoneproject.data.model

import com.example.capstoneproject.data.entities.product.Product

data class ProductsItem(
    val id: String,
    val user: String,
    val title: String,
    val price: String,
    val description: String,
    val category: String,
    val image: String,
    val rate: String,
    val count: String,
)

fun ProductsItem.toProduct() = Product(
    productTitle = title,
    productDescription = description,
    productCategory = category,
    productPrice = price,
    productImage = image
)