package com.example.capstoneproject.data.local.user

import androidx.room.*
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.data.model.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM ${Constant.USER_DB_NAME}")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM ${Constant.USER_DB_NAME} WHERE id= :userId ")
    suspend fun getCurrentUser(userId: Int): User

    @Query("SELECT * FROM ${Constant.USER_DB_NAME} WHERE username = :userName AND password =:userPassword")
    suspend fun login(userName: String, userPassword: String): User
}