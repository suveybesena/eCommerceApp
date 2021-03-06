package com.example.capstoneproject.data.repository

import com.example.capstoneproject.data.model.CRUDResponse
import com.example.capstoneproject.data.model.ProductsItem
import com.example.capstoneproject.data.remote.ProductAPI
import com.example.capstoneproject.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val productAPI: ProductAPI

) : RemoteRepository {
    override suspend fun addProductToBag(
        user: String,
        title: String,
        price: Double,
        description: String,
        category: String,
        image: String,
        rate: Double,
        count: Int,
        sale_state: Int
    ): CRUDResponse =
        productAPI.addProductToBag(
            user,
            title,
            price,
            description,
            category,
            image,
            rate,
            count,
            sale_state
        )

    override suspend fun getProducts(): List<ProductsItem> =
        productAPI.getProducts()

    override suspend fun getProductsByCategories(
        user: String,
        categoryName: String
    ): List<ProductsItem> =
        productAPI.getProductsByCategories(user, categoryName)

    override suspend fun getSaleProducts(): List<ProductsItem> =
        productAPI.getSaleProducts()

    override suspend fun getProductsByName(user: String): List<ProductsItem> =
        productAPI.getProductsByName(user)

    override suspend fun getBagProductsByUser(user: String): List<ProductsItem> =
        productAPI.getBagProductsByUser(user)

    override suspend fun deleteFromBag(id: Int): CRUDResponse =
        productAPI.deleteFromBag(id)

    override suspend fun getCategoriesByUser(user: String): List<String> =
        productAPI.getCategoriesByUser(user)
}