package com.example.capstoneproject.data.local.product.basket

import androidx.room.*
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.data.model.product.Basket
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(basketItem: Basket)

    @Update
    suspend fun updateProduct(basketItem: Basket)

    @Delete
    suspend fun deleteProduct(basketItem: Basket)

    @Query("SELECT * FROM ${Constant.BASKET_DB_NAME} WHERE currentUserId = :userId")
    fun getBasketProducts(userId: String): Flow<List<Basket>>
}