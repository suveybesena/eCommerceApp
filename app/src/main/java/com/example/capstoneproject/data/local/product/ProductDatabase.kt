package com.example.capstoneproject.data.local.product

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.example.capstoneproject.data.model.Converters
import com.example.capstoneproject.data.model.product.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDAO(): ProductDAO
}