package com.example.capstoneproject.data.local.product

import androidx.room.*
import com.example.capstoneproject.common.extensions.Constant.PRODUCT_DB_NAME
import com.example.capstoneproject.data.model.product.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(noteDetail: Product)

    @Delete
    suspend fun deleteProduct(noteDetail: Product)

    @Query("SELECT * FROM $PRODUCT_DB_NAME")
    fun getAllProducts(): Flow<List<Product>>
}