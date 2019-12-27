package com.lyhoangvinh.simple.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.text.TextUtils
import android.transition.ChangeBounds
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.animation.addListener
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.math.MathUtils
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.response.ResponseFourZip
import com.lyhoangvinh.simple.data.source.base.PlainResponseFourConsumer
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import kotlin.math.hypot

fun ImageView.loadImageIssues(url: String) {
//    Picasso.get()
//        .load(url)
//        .placeholder(R.drawable.ic_placeholder_rectangle_200px)
//        .error(R.drawable.ic_placeholder_rectangle_200px)
//        .centerCrop()
//        .fit()
//        .into(this)
}

fun ImageView.loadImage(url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_placeholder_rectangle_200px)
        .error(R.drawable.poster_show_not_available)
        .fit()
        .centerCrop()
        .into(this, object : Callback {
            override fun onSuccess() {

            }

            override fun onError(e: java.lang.Exception?) {
                loadImage(url)
            }
        })
}

fun ImageView.loadImageNotFit(url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_placeholder_rectangle_200px)
        .error(R.drawable.poster_show_not_available)
        .into(this, object : Callback {
            override fun onSuccess() {

            }

            override fun onError(e: java.lang.Exception?) {
                loadImageNotFit(url)
            }
        })
}

fun Activity.createDialog(): Dialog? {
    val progressDialog = Dialog(this)
    progressDialog.let {
        it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        it.setContentView(R.layout.progress_dialog)
        it.setCancelable(false)
        it.setCanceledOnTouchOutside(false)
        return it
    }
}

@SuppressLint("SimpleDateFormat")
fun TextView.formatDate(time: Long) {
    try {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val netDate = Date(time)
        this.text = sdf.format(netDate)
    } catch (e: Exception) {
        this.text = e.toString()
    }
}

fun getAppDateFormatter(createdDate: String): String? {
    var out: String? = null
    var dateFormatter: Date? = null
    if (!TextUtils.isEmpty(createdDate)) {
        dateFormatter = parseToDate(createdDate)
    }
    if (dateFormatter != null) {
        out = formatToDate(dateFormatter)
    }
    if (TextUtils.isEmpty(out)) {
        out = createdDate
    }
    return out
}

fun formatToDate(date: Date): String {
    var result = ""
    try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        result = sdf.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return result
}

fun parseToDate(date: String?): Date? {
    var d: Date? = null
    if (!TextUtils.isEmpty(date)) {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        //sdf.setTimeZone(...);
        try {
            d = sdf.parse(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

    }
    return d
}

fun Activity.showToastMessage(message: String) {
    if (!message.isEmpty()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.showToastMessage(message: String) {
    if (this.context != null && !message.isEmpty()) {
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showKeyboard(editText: EditText) {
    activity?.showKeyboard(editText)
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboard(yourEditText: EditText) {
    try {
        val input = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        input.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        input.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT)
    } catch (ignored: Exception) {
    }
}

fun View.setVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.setVisibilityAnim(isVisible: Boolean) {
    val cx = width / 2
    val cy = height / 2

    val radius = hypot(cx.toFloat(), cy.toFloat())
    val anim = if (isVisible) {
        ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, radius)
    } else {
        ViewAnimationUtils.createCircularReveal(this, cx, cy, radius, 0f)
    }
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            setVisibility(isVisible)
        }
    })
    anim.start()
}

fun Fragment.addAnimations(): Fragment {
    return this.apply {
        val slideTransition = Slide(Gravity.END)
        slideTransition.duration = 300L
        val changeBoundsTransition = ChangeBounds()
        changeBoundsTransition.duration = 300L
        enterTransition = slideTransition
        allowEnterTransitionOverlap = false
        allowReturnTransitionOverlap = false
        sharedElementEnterTransition = changeBoundsTransition
    }
}

fun Activity.startActivityTransition(cls: Class<*>, finishAct: Boolean) {
    this.let {
        val pairs = TransitionUtil.createSafeTransitionParticipants(it, true)
        val intent = Intent(it, cls)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(it, *pairs)
        it.startActivity(intent, options.toBundle())
        if (finishAct)
            Handler().postDelayed({ it.finish() }, 300L)
    }
}

inline fun <reified T> genericCastOrNull(anything: Any): T {
    return anything as T
}

fun TextView.startCollapsingAnimation(finalText: String, duration: Long) {
    this.startCustomAnimation(true, finalText, duration)
}

fun TextView.cancelAnimation() {
    val o = this.tag
    if (o != null && o is ValueAnimator) {
        o.cancel()
    }
}

fun TextView.startCustomAnimation(isCollapsing: Boolean, finalText: String, duration: Long) {
    cancelAnimation()
    val mStartText = this.text
    val animator =
        if (isCollapsing) ValueAnimator.ofFloat(1.0f, 0.0f)
        else ValueAnimator.ofFloat(0.0f, 1.0f)
    animator.addUpdateListener {
        val currentValue = it.animatedValue as Float
        val ended =
            (isCollapsing && currentValue == 0.0f) || (!isCollapsing && currentValue == 1.0f)
        if (ended) {
            this.text = finalText
        } else {
            val n = (mStartText.length * currentValue).toInt()
            val text = mStartText.substring(0, n)
            if (text != this.text) {
                this.text = text
            }
        }
    }
    this.tag = animator
    animator.duration = duration
    animator.start()
}

@SuppressLint("CheckResult")
fun EditText.textChanges(onTextChangeListener: (String) -> Unit) {
    RxTextView.textChanges(this).debounce(1000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .skip(1)
        .subscribe { charSequence -> onTextChangeListener.invoke(charSequence.toString()) }
}


@Suppress("DEPRECATION")
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.setStatusBarGradients() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val background = resources.getDrawable(R.drawable.bg_gradient_evening_sunshine)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
        window.navigationBarColor = resources.getColor(android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }
}

fun Fragment.setStatusBarGradient() {
    activity?.setStatusBarGradients()
}

fun Activity.setStatusBarColor(@ColorRes id: Int) {
    window.statusBarColor = ContextCompat.getColor(this, id)
}

fun Fragment.setStatusBarColor(@ColorRes id: Int) {
    activity?.setStatusBarColor(id)
}

fun Activity.removeStatusBar() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun Fragment.removeStatusBar() {
    activity?.removeStatusBar()
}

fun Context.calculateNoOfColumnsShow(): Int {
    val displayMetrics = this.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return (dpWidth / 220).toInt()
}

fun Activity.calculateNoOfColumnsShow(): Int = applicationContext.calculateNoOfColumnsShow()

fun Fragment.calculateNoOfColumnsShow(): Int = activity?.calculateNoOfColumnsShow()!!

fun View.setDelayedClickable(clickable: Boolean, delayedMillis: Long) {
    if (delayedMillis > 0) {
        val weakView = WeakReference<View>(this)
        weakView.get()?.postDelayed({
            weakView.get()?.isClickable = clickable
        }, delayedMillis)
    } else {
        isClickable = clickable
    }
}

fun View.setDelayedClickable(clickable: Boolean) {
    setDelayedClickable(clickable, 300L)
}

fun <T> newPlainConsumer(consumer: (T) -> Unit) = object : PlainConsumer<T> {
    override fun accept(t: T) {
        consumer.invoke(t)
    }
}

fun <T1, T2, T3, T4> newPlainResponseFourConsumer(consumer: (ResponseFourZip<T1, T2, T3, T4>) -> Unit) = object :
    PlainResponseFourConsumer<T1, T2, T3, T4> {
    override fun accept(dto: ResponseFourZip<T1, T2, T3, T4>) {
        consumer.invoke(dto)
    }
}

/**
 * Excute room
</T> */

//fun execute(action: () -> Unit) {
//    Completable.fromAction {
//        action.invoke()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe()
//}