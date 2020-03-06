package com.lyhoangvinh.simple.ui.features.avg.detail

import android.os.Bundle
import android.webkit.*
import androidx.lifecycle.LifecycleOwner
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.ui.observableUi.ConnectionObservable
import javax.inject.Inject

class DetailViewModel @Inject constructor(val connectionObservable: ConnectionObservable) : BaseViewModel() {

    var url = ""

    var webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress < 100) {
                publishState(State.loading())
            }
            if (newProgress == 100) {
                publishState(State.success())
            }
        }
    }

    var webViewClient: WebViewClient = object : WebViewClient() {

        @Suppress("DEPRECATION")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return if (url!!.endsWith(".mp3")) {
                navigatorHelper.navigateFromType("audio/*",url)
                true
            } else if (url.endsWith(".mp4") || url.endsWith(".3gp")) {
                navigatorHelper.navigateFromType("video/*", url)
                true
            } else {
                super.shouldOverrideUrlLoading(view, url)
            }
        }
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        connectionObservable.observableConnection(lifecycleOwner)
        if (bundle != null) {
            url = bundle.getString(Constants.EXTRA_DATA)!!
        }
    }
}