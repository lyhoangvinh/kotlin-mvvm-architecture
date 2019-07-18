package com.lyhoangvinh.simple.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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