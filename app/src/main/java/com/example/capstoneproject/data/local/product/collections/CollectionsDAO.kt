package com.example.capstoneproject.data.local.product.collections

import androidx.room.*
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.data.model.product.Collection
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(collection: Collection)

    @Update
    suspend fun updateProduct(collection: Collection)

    @Delete
    suspend fun deleteProduct(collection: Collection)

    @Query("SELECT * FROM ${Constant.COLLECTION_DB_NAME} WHERE currentUserId = :userId")
    fun getCollectionProducts(userId: String): Flow<List<Collection>>
}