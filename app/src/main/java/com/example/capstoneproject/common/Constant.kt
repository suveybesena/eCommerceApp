package com.example.capstoneproject.common

object Constant {
    const val SHARED_PREF_KEY = "currentUser"
    const val SET_SHARED_PREF_KEY = "getSharedPref"

    const val PRODUCT_DB_NAME = "product_db"
    const val BASKET_DB_NAME = "basket_db"
    const val CAPSTONE_DB_NAME = "capstone_db"
    const val PURCHASED_DB_NAME = "purchased_db"
    const val FAVORITES_DB_NAME = "favorites_db"
    const val COLLECTION_DB_NAME = "collection_db"
    const val USER_DB_NAME = "user_db"
    const val LOCAL_DB_PRODUCT_IMAGE = "productImage"

    const val LOCAL_DB_PRODUCT_PRICE = "productPrice"
    const val LOCAL_DB_PRODUCT_CATEGORY = "productCategory"
    const val LOCAL_DB_PRODUCT_DESCRIPTION = "productDescription"
    const val LOCAL_DB_PRODUCT_TITLE = "productTitle"
    const val LOCAL_DB_PRODUCT_COUNT = "productCount"
    const val LOCAL_DB_PRODUCT_CURRENT_USER_UD = "currentUserId"
    const val LOCAL_DB_PRODUCT_ID = "id"

    const val LOCAL_DB_USER_USERNAME = "username"
    const val LOCAL_DB_USER_ADDRESS = "address"
    const val LOCAL_DB_CURRENTUSER_ID = "currentuser"
    const val LOCAL_DB_USER_EMAIL = "email"
    const val LOCAL_DB_USER_PASSWORD = "password"
    const val LOCAL_DB_USER_PHONE = "phone"
    const val LOCAL_DB_USER_ID = "id"

    const val BASE_URL = "https://canerture.com/api/ecommerce/"

    const val AUTH_ENDPOINT = "sign_in.php"
    const val ADD_USER_ENDPOINT = "sign_up.php"
    const val ALL_PRODUCT_BY_USERNAME_ENDPOINT = "get_products_by_user.php"
    const val ALL_PRODUCTS_IN_CATEGORY_ENDPOINT = "get_products_by_user_and_category.php"
    const val ALL_SALE_PRODUCTS_ENDPOINT = "get_sale_products.php"

    const val ALL_PRODUCTS_ENDPOINT = "get_products.php"
    const val ADD_PRODUCTS_TO_BAG_ENDPOINT = "add_to_bag.php"
    const val GET_PRODUCTS_FROM_BAG_BY_USER_ENDPOINT = "get_bag_products_by_user.php"
    const val DELETE_BAG_ITEM_ENDPOINT = "delete_to_bag.php"
    const val ALL_ORDERS_OF_USER_ENDPOINT = "carts/user/{user_id}"

    const val STRING_ARGS_ID = "category"
    const val PARCELABLE_ARGS_ID = "detail"

    const val SUCCESS_REGISTER = "Successfully registered."
    const val SUCCESS_LOGIN = "Successfully logged in."
    const val SUCCESS_ADDED_CART = "The product added to the cart."
    const val FAILED_LOGIN = "Login failed."
    const val INSERT_PRODUCT_COLLECTIONS = "The product added to collections."
    const val INSERT_PRODUCT_FAVORITES = "The product added to favourites."

    const val CHANNEL_ID = "channelId"
    const val CHANNEL_NAME = "channelName"
    const val CHANNEL_INTRO = "channelIntroduction"
    const val BASKET_REMINDER_TITLE = "You forgot an item in your cart."
    const val BASKET_REMINDER_DESC = "Products are running out, buy without missing out."


}