package com.lyhoangvinh.simple.utils

import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.webkit.WebChromeClient
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import android.widget.EditText
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.*
import com.lyhoangvinh.simple.ui.base.interfaces.OnClickable
import com.lyhoangvinh.simple.ui.widget.RotateLoading
import com.lyhoangvinh.simple.ui.widget.newton.NewtonCradleLoading

object BindingUtils {

    @JvmStatic
    @BindingAdapter("loadImageURL")
    fun loadImageURL(imageView: ImageView, url: String?) {
        imageView.loadImage(url!!)
    }

    @JvmStatic
    @BindingAdapter("loadImageNotFit")
    fun loadImageNotFit(imageView: ImageView, url: String?) {
        imageView.loadImageNotFit(url!!)
    }

    @JvmStatic
    @BindingAdapter("loadHistoryImageURL")
    fun loadHistoryImageURL(imageView: ImageView, url: String?) {
//        Picasso.get()
//            .load(url)
//            .placeholder(R.drawable.ic_access_time_black_24dp)
//            .error(R.drawable.ic_access_time_black_24dp)
//            .centerCrop()
//            .resize(30,30)
//            .fit()
//            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("formatDate")
    fun formatDate(textView: TextView, date: Long?) {
        textView.formatDate(date!!)
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
    @BindingAdapter("setAnimatedVisibility")
    fun setAnimatedVisibility(target: View, isVisible: Boolean) {
        val animFadeIn = AnimationUtils.loadAnimation(target.context, R.anim.fade_in)
        val animFadeOut = AnimationUtils.loadAnimation(target.context, R.anim.fade_out)
        target.visibility =
            if (isVisible) View.VISIBLE else View.GONE
        target.animation =
            if (isVisible) animFadeIn else animFadeOut
    }

    @JvmStatic
    @BindingAdapter("setAnimatedVisibility")
    fun setAnimatedVisibility(target: View, visible: VisibilityView?) {
        if (visible == null){
            return
        }
        val animFadeIn = AnimationUtils.loadAnimation(target.context, R.anim.fade_in)
        val animFadeOut = AnimationUtils.loadAnimation(target.context, R.anim.fade_out)
        target.visibility =
            if (visible.isVisible) View.VISIBLE else View.GONE
        target.animation =
            if (visible.isVisible) animFadeIn else animFadeOut
    }

    @JvmStatic
    @BindingAdapter("startRotateLoading")
    fun startRotateLoading(rotateLoading: RotateLoading, isStart: Boolean) {
        if (rotateLoading.isStart) {
            rotateLoading.stop()
        } else {
            rotateLoading.start()
        }
    }

    @JvmStatic
    @BindingAdapter("startNewtonCradleLoading")
    fun startNewtonCradleLoading(newtonCradleLoading: NewtonCradleLoading, state: State?) {
        if (state?.status == Status.LOADING) {
            if (!newtonCradleLoading.isStart)
                newtonCradleLoading.start()
        } else {
            if (newtonCradleLoading.isStart)
                newtonCradleLoading.stop()
        }
    }

    @JvmStatic
    @BindingAdapter("observableConnection")
    fun observableConnection(view: View, connection: Connection?) {
        if (connection == null) {
            return
        }
        view.setVisibility(!connection.isConnected)
    }

    @JvmStatic
    @BindingAdapter("observableData")
    fun observableData(view: View, data: DataEmpty?) {
        view.setVisibility(data != null && data.isEmpty)
    }

    @JvmStatic
    @BindingAdapter("messageDataEmpty")
    fun messageDataEmpty(textView: TextView, keyword: String?) {
        textView.text = String.format(textView.context.getString(R.string.could_not_found), keyword)
    }

    @JvmStatic
    @BindingAdapter("setButtonSearchClickable")
    fun setButtonSearchClickable(view: View, listener: OnClickable?) {
        view.setOnClickListener {
            if (listener != null) {
                view.setDelayedClickable(false)
                listener.accept()
                view.setDelayedClickable(true)
            }
            view.context.hideKeyboard(view)
        }
    }

    @JvmStatic
    @BindingAdapter("setOnEditorActionListener")
    fun setOnEditorActionListener(editText: EditText, listener: OnClickable?) {
        editText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    listener?.accept()
                    editText.context.hideKeyboard(editText)
                    return true
                }
                return false
            }
        })
    }

    @JvmStatic
    @BindingAdapter("setTextChanges")
    fun setTextChanges(editText: EditText, listener: OnClickable?) {
        editText.textChanges {
            listener?.accept()
        }
    }

    @JvmStatic
    @BindingAdapter("setOnClearTextClickable")
    fun setOnClearTextClickable(view: View, listener: OnClickable?) {
        view.setOnClickListener {
            listener?.accept()
        }
    }
}