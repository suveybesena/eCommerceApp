package com.example.capstoneproject.data.local.product.purchased

import androidx.room.*
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.data.entities.product.Purchased
import kotlinx.coroutines.flow.Flow


@Dao
interface PurchasedDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(purchased: Purchased)

    @Update
    suspend fun updateProduct(purchased: Purchased)

    @Delete
    suspend fun deleteProduct(purchased: Purchased)

    @Query("SELECT * FROM ${Constant.PURCHASED_DB_NAME} WHERE currentUserId = :userId")
    fun getPurchasedProducts(userId: String): Flow<List<Purchased>>
}