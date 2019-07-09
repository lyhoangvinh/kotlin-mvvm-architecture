package com.lyhoangvinh.simple.utils

import android.text.TextUtils
import android.widget.ImageView
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