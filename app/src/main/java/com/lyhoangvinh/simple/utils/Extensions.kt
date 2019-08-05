package com.lyhoangvinh.simple.utils

import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.text.TextUtils
import android.transition.ChangeBounds
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.lyhoangvinh.simple.R
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImageIssues(url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_placeholder_rectangle_200px)
        .error(R.drawable.ic_placeholder_rectangle_200px)
        .centerCrop()
        .fit()
        .into(this)
}

fun ImageView.loadImage(url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_placeholder_rectangle_200px)
        .error(R.drawable.ic_placeholder_rectangle_200px)
        .centerCrop()
        .fit()
        .into(this)
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
            d = sdf.parse(date)
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
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
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

inline fun <reified T> genericCastOrNull(anything: Any):T {
    return anything as T
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
        val ended = (isCollapsing && currentValue == 0.0f) || (!isCollapsing && currentValue == 1.0f)
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

fun Fragment.setStatusBarGradient() {
    activity?.setStatusBarGradients()
}

fun Activity.setStatusBarColor(@ColorRes id: Int) {
    window.statusBarColor = ContextCompat.getColor(this, id)
}

fun Fragment.setStatusBarColor(@ColorRes id: Int) {
    activity?.setStatusBarColor(id)
}

fun Activity.removeStatusBar(){
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Fragment.removeStatusBar(){
    activity?.removeStatusBar()
}

