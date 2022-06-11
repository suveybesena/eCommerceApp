package com.example.capstoneproject.data.local.user

import androidx.room.*
import com.example.capstoneproject.common.extensions.Constant
import com.example.capstoneproject.data.model.user.UserItem
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserItem)

    @Update
    suspend fun updateUser(user: UserItem)

    @Delete
    suspend fun deleteUser(user: UserItem)

    @Query("SELECT * FROM ${Constant.USER_DB_NAME}")
    fun getAllUsers(): Flow<List<UserItem>>
}