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

    @JvmStatic
    @BindingAdapter("setTotalView")
    fun setTotalView(textView: TextView, totalView: Long?) {
        textView.text = String.format("TotalView: %s", totalView)
    }

    @JvmStatic
    @BindingAdapter("setTotalView")
    fun setTotalView(textView: TextView, totalView: Int?) {
        textView.text = String.format("TotalView: %s", totalView)
    }

    @JvmStatic
    @BindingAdapter("setTotalLike")
    fun setTotalLike(textView: TextView, totalLike: Long?) {
        textView.text = String.format("Likes: %s", totalLike)
    }

    @JvmStatic
    @BindingAdapter("setVideoCount")
    fun setVideoCount(textView: TextView, videoCount: Int?) {
        textView.text = String.format("Video count: %s", videoCount)
    }

}