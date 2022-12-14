package com.yo.newsapp.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yo.newsapp.R
import com.yo.newsapp.model.Article

fun shareNews(context: Context?, article: Article){
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT,article.urlToImage)
        putExtra(Intent.EXTRA_STREAM,article.urlToImage)
        putExtra(Intent.EXTRA_TITLE, article.title)
        type = "image/*"
    }
    context?.startActivity(Intent.createChooser(intent,"Share News On "))
}

fun getCircularDrawable(context: Context): CircularProgressDrawable{

    return  CircularProgressDrawable(context).apply {
        strokeWidth= 8f
        centerRadius = 48f
        setTint(context.resources.getColor(com.google.android.material.R.color.m3_ref_palette_black))
        start()
    }
}

fun ImageView.loadImage(url: String,progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_foreground)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView,url: String?){
    if(url != null){
        imageView.loadImage(url!!, getCircularDrawable(imageView.context))
    }
}