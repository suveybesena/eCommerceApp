package com.example.capstoneproject.data.entities.product

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.capstoneproject.common.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constant.PRODUCT_DB_NAME)
data class Product(
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_TITLE)
    var productTitle: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_DESCRIPTION)
    var productDescription: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_CATEGORY)
    var productCategory: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_PRICE)
    val productPrice: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_IMAGE)
    val productImage: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.LOCAL_DB_PRODUCT_ID)
    val productId: Int? = null
) : Parcelable