package com.example.capstoneproject.data.entities.user

import kotlin.random.Random

data class Geolocation(
    val lat: String = Random.nextDouble(-90.0, 90.0).toString(),
    val long: String = Random.nextDouble(-180.0, 180.0).toString()
)