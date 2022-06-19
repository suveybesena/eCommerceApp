package com.example.capstoneproject.data.remote

import com.example.capstoneproject.common.Constant.ADD_USER_ENDPOINT
import com.example.capstoneproject.common.Constant.AUTH_ENDPOINT
import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.data.model.CRUDResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {
    @POST(AUTH_ENDPOINT)
    @FormUrlEncoded
    fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): User

    @POST(ADD_USER_ENDPOINT)
    @FormUrlEncoded
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("address") address: String
    ): CRUDResponse
}