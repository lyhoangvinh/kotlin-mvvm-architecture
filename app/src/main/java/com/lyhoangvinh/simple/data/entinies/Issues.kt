package com.lyhoangvinh.simple.data.entinies

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Issues(

    @PrimaryKey
    @SerializedName("id")
    var id: Long,

    @SerializedName("date_added")
    var dateAdded: String? = "",

    @SerializedName("date_last_updated")
    var dateLastUpdated: String? = ""


)