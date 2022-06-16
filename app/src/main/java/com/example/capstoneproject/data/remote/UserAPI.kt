package com.example.capstoneproject.data.remote

import com.example.capstoneproject.common.Constant.ADD_USER_ENDPOINT
import com.example.capstoneproject.common.Constant.ALL_USERS_ENDPOINT
import com.example.capstoneproject.common.Constant.AUTH_ENDPOINT
import com.example.capstoneproject.data.entities.user.UserItem
import retrofit2.http.*

interface UserAPI {

    @POST(ADD_USER_ENDPOINT)
    suspend fun signUp(@Body user: UserItem): UserItem

    @FormUrlEncoded
    @POST(AUTH_ENDPOINT)
    suspend fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): Map<String, String>

    @Headers("withToken: true")
    @GET(ALL_USERS_ENDPOINT)
    suspend fun getUsers(): List<UserItem>
}