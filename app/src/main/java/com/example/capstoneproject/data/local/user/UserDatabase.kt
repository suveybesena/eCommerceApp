package com.example.capstoneproject.data.local.user

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.capstoneproject.data.model.Converters
import com.example.capstoneproject.data.model.user.UserItem

@Database(entities = [UserItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
}