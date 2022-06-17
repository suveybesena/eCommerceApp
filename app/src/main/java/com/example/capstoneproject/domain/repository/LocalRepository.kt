package com.example.capstoneproject.domain.repository

import com.example.capstoneproject.data.entities.product.*
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.data.model.ProductsItem
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertUserToDatabase(user: User)
    suspend fun insertProductToFavorites(favorites: Favorites)
    suspend fun insertProductToCollections(collection: Collection)
    suspend fun insertProductToPurchased(purchased: Purchased)
    suspend fun insertProductToBasket(basketItem: Basket)
    suspend fun insertProductToDatabase(product: Product)
    fun getFavoritesProducts(userId: String): Flow<List<Favorites>>
    fun getBasketProducts(userId: String): Flow<List<Basket>>
    fun getCollectionProducts(userId: String): Flow<List<Collection>>
    suspend fun login(userName: String, userPassword: String): User
    fun getPurchasedProducts(userId: String): Flow<List<Purchased>>
    suspend fun getCurrentUser(userId: String): User
    fun getAllProduct(): Flow<List<Product>>
    fun getProductByDescription(description: String): Flow<List<Product>>
    suspend fun deleteBasketProduct(basketItem: Basket)
    suspend fun deleteFavoritesProduct(favorites: Favorites)
    suspend fun deleteCollectionProduct(collection: Collection)

}