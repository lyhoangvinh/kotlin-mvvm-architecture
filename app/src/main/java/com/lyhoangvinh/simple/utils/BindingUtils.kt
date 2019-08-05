package com.lyhoangvinh.simple.utils

import android.view.View
import android.webkit.WebChromeClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import com.lyhoangvinh.simple.R

object BindingUtils {

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

    @JvmStatic
    @BindingAdapter("setWebViewClient")
    fun setWebViewClient(view: WebView, client: WebViewClient) {
        view.webViewClient = client
    }

    @JvmStatic
    @BindingAdapter("setWebChromeClient")
    fun setWebChromeClient(view: WebView, chromeClient: WebChromeClient) {
        view.webChromeClient = chromeClient
    }

    @JvmStatic
    @BindingAdapter("loadWebViewUrl")
    fun loadWebViewUrl(view: WebView, url: String) {
        view.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(true)
            displayZoomControls = true
            builtInZoomControls = true
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        view.loadUrl(url)
    }

    @JvmStatic
    @BindingAdapter("setStartCollapsingAnimation")
    fun setStartCollapsingAnimation(view: TextView, text: String) {
        view.startCollapsingAnimation(text, 500L)
    }

    @JvmStatic
    @BindingAdapter("android:animatedVisibility")
    fun setAnimatedVisibility(target: View, isVisible: Boolean) {
        target.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}