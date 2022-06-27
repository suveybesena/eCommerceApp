package com.example.capstoneproject.domain.repository

import com.example.capstoneproject.data.model.CRUDResponse
import com.example.capstoneproject.data.model.ProductsItem
import retrofit2.http.Field

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

    suspend fun getBagProductsByUser(
        user: String,
    ): List<ProductsItem>

    suspend fun deleteFromBag(
        @Field("id") id: Int
    ): CRUDResponse

    suspend fun getCategoriesByUser(user: String): List<String>
}