package com.example.capstoneproject.common.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.capstoneproject.R

fun ImageView.downloadImage(
    imageUrl: String,
    @DrawableRes errorImage: Int = R.drawable.imageerror
) {
    Glide.with(this.context).load(imageUrl).error(errorImage)
        .into(this)
}