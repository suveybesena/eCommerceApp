package com.example.capstoneproject.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.capstoneproject.common.extensions.Constant

@Entity(tableName = Constant.USER_DB_NAME)
data class UserItem(
    //@ColumnInfo(name = Constant.LOCAL_DB_USER_ADDRESS)
    //val address: Address,
    @ColumnInfo(name = Constant.LOCAL_DB_USER_EMAIL)
    val email: String,
    @ColumnInfo(name = Constant.LOCAL_DB_USER_NAME)
    val name: Name,
    @ColumnInfo(name = Constant.LOCAL_DB_USER_PASSWORD)
    val password: String,
    @ColumnInfo(name = Constant.LOCAL_DB_USER_PHONE)
    val phone: String,
    @ColumnInfo(name = Constant.LOCAL_DB_USER_USERNAME)
    val username: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constant.LOCAL_DB_USER_ID)
    val id: Int? = null
)