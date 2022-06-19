package com.example.capstoneproject.data.local.product.favorites

import androidx.room.*
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.data.entities.product.Favorites
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(favorites: Favorites)

    @Update
    suspend fun updateProduct(favorites: Favorites)

    @Delete
    suspend fun deleteProduct(favorites: Favorites)

    @Query("SELECT * FROM ${Constant.FAVORITES_DB_NAME} WHERE currentUserId = :userId")
    fun getFavoritesProducts(userId: String): Flow<List<Favorites>>
}