package com.example.capstoneproject.data.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.capstoneproject.common.Constant

@Entity(tableName = Constant.USER_DB_NAME)
data class User(
    @ColumnInfo(name = Constant.LOCAL_DB_USER_USERNAME)
    val userName: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_USER_EMAIL)
    val userMail: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_USER_PASSWORD)
    val userPassword: String?,
    @ColumnInfo(name = Constant.LOCAL_DB_USER_PHONE)
    val userPhone: String? = null,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.LOCAL_DB_USER_ID)
    val userId: Int? = null
)