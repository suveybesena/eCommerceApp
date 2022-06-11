package com.example.capstoneproject.data.remote

import com.example.capstoneproject.data.model.product.Product
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductAPI {
    @GET("products")
    fun getAllProducts(): Flow<List<Product>>

    @GET("products?sort")
    fun sortProducts(
        @Query("desc")
        categoryName: String
    ): Flow<List<Product>>
}