package com.example.capstoneproject.data.model.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.capstoneproject.common.extensions.Constant


@Entity(tableName = Constant.PRODUCT_DB_NAME)
data class Product(
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_TITLE)
    var title: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_PRICE)
    var price: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_DESC)
    var description: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_CATEGORY)
    var category: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_IMAGE)
    var image: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_ID)
    val id: Int? = null
)
