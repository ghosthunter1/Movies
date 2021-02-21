package com.app.movies

import android.content.res.Resources
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.fromUrl(url: String) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions.centerCropTransform())
        .into(this)
}


val Int.dpToPx : Int
    get() = this * Resources.getSystem().displayMetrics.density.toInt()

