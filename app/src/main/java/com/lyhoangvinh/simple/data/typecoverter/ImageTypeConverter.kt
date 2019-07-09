package com.lyhoangvinh.simple.data.typecoverter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.lyhoangvinh.simple.data.entinies.comic.ImageAll

class ImageTypeConverter {

    @TypeConverter
    fun stringToImageAll(data: String?): ImageAll? {
        return Gson().fromJson(data, ImageAll::class.java)
    }

    @TypeConverter
    fun imageAllToString(data: ImageAll?): String? {
        return Gson().toJson(data)
    }
}