package com.lyhoangvinh.simple.data.entities.comic

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class ImageAll(
    @SerializedName("medium_url")
    var medium_url: String? = ""
)