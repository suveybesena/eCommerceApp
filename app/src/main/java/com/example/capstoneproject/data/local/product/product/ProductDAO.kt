package com.example.capstoneproject.data.local.product.product

import androidx.room.*
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.data.entities.product.Product
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM ${Constant.PRODUCT_DB_NAME}")
    fun getAllProduct(): Flow<List<Product>>

    @Query("SELECT * FROM ${Constant.PRODUCT_DB_NAME} WHERE productCategory = :category")
    fun getProductByCategories(category: String): Flow<List<Product>>

    @Query("SELECT * FROM ${Constant.PRODUCT_DB_NAME} WHERE productDescription LIKE :description")
    fun getProductByDescription(description: String): Flow<List<Product>>
}