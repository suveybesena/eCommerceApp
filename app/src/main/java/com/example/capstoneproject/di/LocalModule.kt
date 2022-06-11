package com.example.capstoneproject.di

import android.content.Context
import androidx.room.Room
import com.example.capstoneproject.common.extensions.Constant
import com.example.capstoneproject.data.local.product.ProductDAO
import com.example.capstoneproject.data.local.product.ProductDatabase
import com.example.capstoneproject.data.local.user.UserDAO
import com.example.capstoneproject.data.local.user.UserDatabase
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
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase =
        Room.databaseBuilder(context, ProductDatabase::class.java, Constant.PRODUCT_DB_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideProductDAO(productDatabase: ProductDatabase): ProductDAO =
        productDatabase.productDAO()

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, Constant.USER_DB_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideUserDAO(userDatabase: UserDatabase): UserDAO =
        userDatabase.userDAO()
}