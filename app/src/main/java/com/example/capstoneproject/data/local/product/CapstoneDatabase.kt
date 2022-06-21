package com.example.capstoneproject.data.local.product

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.capstoneproject.data.entities.product.*
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.data.local.product.basket.BasketDAO
import com.example.capstoneproject.data.local.product.collections.CollectionsDAO
import com.example.capstoneproject.data.local.product.favorites.FavoritesDAO
import com.example.capstoneproject.data.local.product.product.ProductDAO
import com.example.capstoneproject.data.local.product.purchased.PurchasedDAO
import com.example.capstoneproject.data.local.user.UserDAO


@Database(
    entities = [Purchased::class, Basket::class, Favorites::class, Collection::class, User::class, Product::class],
    version = 1
)
abstract class CapstoneDatabase : RoomDatabase() {
    abstract fun basketDAO(): BasketDAO
    abstract fun collectionsDAO(): CollectionsDAO
    abstract fun favoritesDAO(): FavoritesDAO
    abstract fun purchasedDAO(): PurchasedDAO
    abstract fun userDAO(): UserDAO
    abstract fun productDAO(): ProductDAO
}