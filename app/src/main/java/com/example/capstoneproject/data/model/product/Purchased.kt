package com.example.capstoneproject.data.model.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.capstoneproject.common.Constant

@Entity(tableName = Constant.PURCHASED_DB_NAME)
data class Purchased(
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_TITLE)
    var productName: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_COUNT)
    val productCount: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_CURRENT_USER_UD)
    val currentUserId: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_PRICE)
    val productPrice: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_IMAGE)
    val productImage: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_ID)
    val productId: Int? = null
)
