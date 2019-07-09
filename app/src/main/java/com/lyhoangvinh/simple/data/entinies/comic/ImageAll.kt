package com.lyhoangvinh.simple.data.entinies.comic

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class ImageAll(
    @SerializedName("medium_url")
    var medium_url: String? = ""
)