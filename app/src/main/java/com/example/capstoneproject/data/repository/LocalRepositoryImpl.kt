package com.example.capstoneproject.data.repository

import com.example.capstoneproject.data.entities.product.*
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.data.local.product.basket.BasketDAO
import com.example.capstoneproject.data.local.product.collections.CollectionsDAO
import com.example.capstoneproject.data.local.product.favorites.FavoritesDAO
import com.example.capstoneproject.data.local.product.product.ProductDAO
import com.example.capstoneproject.data.local.product.purchased.PurchasedDAO
import com.example.capstoneproject.data.local.user.UserDAO
import com.example.capstoneproject.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val basketDAO: BasketDAO,
    private val userDAO: UserDAO,
    private val collectionsDAO: CollectionsDAO,
    private val purchasedDAO: PurchasedDAO,
    private val favoritesDAO: FavoritesDAO,
    private val productDAO: ProductDAO
) : LocalRepository {
    override suspend fun insertUserToDatabase(user: User) {
        userDAO.insertUser(user)
    }

    override suspend fun login(userName: String, userPassword: String): User =
        userDAO.login(userName, userPassword)

    override fun getPurchasedProducts(userId: String): Flow<List<Purchased>> =
        purchasedDAO.getPurchasedProducts(userId)

    override suspend fun getCurrentUser(userId: String): User =
        userDAO.getCurrentUser(userId)

    override fun getAllProduct(): Flow<List<Product>> =
        productDAO.getAllProduct()


    override fun getProductByDescription(description: String): Flow<List<Product>> =
        productDAO.getProductByDescription(description)

    override suspend fun deleteBasketProduct(basketItem: Basket) {
        basketDAO.deleteProduct(basketItem)
    }

    override suspend fun deleteFavoritesProduct(favorites: Favorites) {
        favoritesDAO.deleteProduct(favorites)
    }

    override suspend fun deleteCollectionProduct(collection: Collection) {
        collectionsDAO.deleteProduct(collection)
    }

    override suspend fun insertProductToFavorites(favorites: Favorites) {
        favoritesDAO.insertProduct(favorites)
    }

    override fun getFavoritesProducts(userId: String): Flow<List<Favorites>> =
        favoritesDAO.getFavoritesProducts(userId)

    override fun getBasketProducts(userId: String): Flow<List<Basket>> =
        basketDAO.getBasketProducts(userId)

    override suspend fun insertProductToBasket(basketItem: Basket) {
        basketDAO.insertProduct(basketItem)
    }

    override suspend fun insertProductToDatabase(product: Product) {
        productDAO.insertProduct(product)
    }

    override suspend fun insertProductToCollections(collection: Collection) {
        collectionsDAO.insertProduct(collection)
    }

    override suspend fun insertProductToPurchased(purchased: Purchased) {
        purchasedDAO.insertProduct(purchased)
    }

    override fun getCollectionProducts(userId: String): Flow<List<Collection>> =
        collectionsDAO.getCollectionProducts(userId)
}