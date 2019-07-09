package com.lyhoangvinh.simple.data.entinies.comic

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Volume(
    @PrimaryKey
    @SerializedName("id")
    var id: Long? = 0,

    @SerializedName("name")
    var name: String? = ""
)