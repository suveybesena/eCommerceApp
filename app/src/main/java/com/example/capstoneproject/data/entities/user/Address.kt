package com.example.capstoneproject.data.entities.user

data class Address(
    val city: String = "kilcoole",
    val geolocation: Geolocation,
    val number: Int = 3,
    val street: String = "7835 new road",
    val zipcode: String = "12926-3874"
)