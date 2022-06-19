package com.example.capstoneproject.domain.repository

import com.example.capstoneproject.data.model.CRUDResponse
import com.example.capstoneproject.data.model.ProductsItem

interface RemoteRepository {


    suspend fun addProductToBag(
        user: String,
        title: String,
        price: Double,
        description: String,
        category: String,
        image: String,
        rate: Double,
        count: Int,
        sale_state: Int
    ): CRUDResponse

    suspend fun getProducts(): List<ProductsItem>
    suspend fun getProductsByCategories(
        user: String,
        categoryName: String
    ): List<ProductsItem>

    suspend fun getSaleProducts(): List<ProductsItem>
    suspend fun getProductsByName(user: String): List<ProductsItem>

    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        phone: String,
        address: String
    ): CRUDResponse
}