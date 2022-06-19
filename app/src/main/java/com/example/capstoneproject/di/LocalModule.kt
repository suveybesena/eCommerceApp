package com.example.capstoneproject.di

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Nullable
import androidx.room.Room
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.common.Constant.SET_SHARED_PREF_KEY
import com.example.capstoneproject.data.local.product.CapstoneDatabase
import com.example.capstoneproject.data.local.product.basket.BasketDAO
import com.example.capstoneproject.data.local.product.collections.CollectionsDAO
import com.example.capstoneproject.data.local.product.favorites.FavoritesDAO
import com.example.capstoneproject.data.local.product.product.ProductDAO
import com.example.capstoneproject.data.local.product.purchased.PurchasedDAO
import com.example.capstoneproject.data.local.user.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideCapstoneDatabase(@ApplicationContext context: Context): CapstoneDatabase =
        Room.databaseBuilder(context, CapstoneDatabase::class.java, Constant.CAPSTONE_DB_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providePurchasedDAO(database: CapstoneDatabase): PurchasedDAO =
        database.purchasedDAO()

    @Provides
    @Singleton
    fun provideFavoritesDAO(database: CapstoneDatabase): FavoritesDAO =
        database.favoritesDAO()

    @Provides
    @Singleton
    fun provideCollectionsDAO(database: CapstoneDatabase): CollectionsDAO =
        database.collectionsDAO()

    @Provides
    @Singleton
    fun provideUserDAO(database: CapstoneDatabase): UserDAO =
        database.userDAO()

    @Provides
    @Singleton
    fun provideBasketDAO(database: CapstoneDatabase): BasketDAO =
        database.basketDAO()

    @Provides
    @Singleton
    fun provideProductDAO(database: CapstoneDatabase): ProductDAO =
        database.productDAO()
}