package com.lyhoangvinh.simple.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.lyhoangvinh.simple.R
import com.squareup.picasso.Picasso

object BindingUtil {

    @JvmStatic
    @BindingAdapter("loadImageURL")
    fun loadImageURL(imageView: ImageView, url: String?) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_placeholder_rectangle_200px)
            .error(R.drawable.ic_placeholder_rectangle_200px)
            .centerCrop()
            .fit()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("setAddedDate")
    fun setAddedDate(textView: TextView, date: String?) {
        textView.text = String.format("Added: %s", getAppDateFormatter(date!!))
    }

    @JvmStatic
    @BindingAdapter("setLastUpdated")
    fun setLastUpdated(textView: TextView, date: String?) {
        textView.text = String.format("Last updated: %s", getAppDateFormatter(date!!))
    }
}