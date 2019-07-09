package com.lyhoangvinh.simple.data.typecoverter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.lyhoangvinh.simple.data.entinies.comic.Volume

class VolumeTypeConverter {

    @TypeConverter
    fun stringToImageAll(data: String?): Volume? {
        return Gson().fromJson(data, Volume::class.java)
    }

    @TypeConverter
    fun imageAllToString(data: Volume?): String? {
        return Gson().toJson(data)
    }
}