package com.example.capstoneproject.data.remote


import com.example.capstoneproject.common.Constant.ADD_PRODUCTS_TO_BAG_ENDPOINT
import com.example.capstoneproject.common.Constant.ALL_PRODUCTS_ENDPOINT
import com.example.capstoneproject.common.Constant.ALL_PRODUCTS_IN_CATEGORY_ENDPOINT
import com.example.capstoneproject.common.Constant.ALL_PRODUCT_BY_USERNAME_ENDPOINT
import com.example.capstoneproject.common.Constant.ALL_SALE_PRODUCTS_ENDPOINT
import com.example.capstoneproject.data.model.CRUDResponse
import com.example.capstoneproject.data.model.ProductsItem
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductAPI {
    @POST(ADD_PRODUCTS_TO_BAG_ENDPOINT)
    @FormUrlEncoded
    fun addProductToBag(
        @Field("user") user: String,
        @Field("title") title: String,
        @Field("price") price: Double,
        @Field("description") description: String,
        @Field("category") category: String,
        @Field("image") image: String,
        @Field("rate") rate: Double,
        @Field("count") count: Int,
        @Field("sale_state") sale_state: Int,
    ): CRUDResponse

    @GET(ALL_PRODUCTS_ENDPOINT)
    suspend fun getProducts(): List<ProductsItem>

    @POST(ALL_PRODUCT_BY_USERNAME_ENDPOINT)
    @FormUrlEncoded
    suspend fun getProductsByName(
        @Field("user") user: String
    ): List<ProductsItem>

    @POST(ALL_PRODUCTS_IN_CATEGORY_ENDPOINT)
    @FormUrlEncoded
    suspend fun getProductsByCategories(
        @Field("user") user: String,
        @Field("category") category: String
    ): List<ProductsItem>

    @GET(ALL_SALE_PRODUCTS_ENDPOINT)
    @FormUrlEncoded
    suspend fun getSaleProducts(): List<ProductsItem>
}