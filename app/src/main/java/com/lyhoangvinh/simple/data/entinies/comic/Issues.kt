package com.lyhoangvinh.simple.data.entinies.comic

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Issues(

    @PrimaryKey
    @SerializedName("id")
    var id: Long,

    @SerializedName("date_added")
    var dateAdded: String? = "",

    @SerializedName("date_last_updated")
    var dateLastUpdated: String? = "",

    @SerializedName("image")
    var images: ImageAll,

    @SerializedName("volume")
    var volume: Volume
)