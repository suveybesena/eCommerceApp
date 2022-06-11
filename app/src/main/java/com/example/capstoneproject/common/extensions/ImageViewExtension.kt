package com.example.capstoneproject.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.downloadImage(imageUrl: String) {
    try {
        Glide.with(this.context).load(imageUrl).into(this)
    } catch (e: Exception) {
        println(e.localizedMessage)
    }
}