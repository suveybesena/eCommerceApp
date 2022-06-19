package com.example.capstoneproject.data.local.user

import androidx.room.*
import com.example.capstoneproject.common.Constant
import com.example.capstoneproject.data.entities.user.User

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM ${Constant.USER_DB_NAME} WHERE currentuser= :userId ")
    suspend fun getCurrentUser(userId: String): User

    @Query("SELECT * FROM ${Constant.USER_DB_NAME} WHERE email = :userEmail AND password =:userPassword")
    suspend fun login(userEmail: String, userPassword: String): User
}