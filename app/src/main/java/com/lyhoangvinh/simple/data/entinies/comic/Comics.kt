package com.lyhoangvinh.simple.data.entinies.comic

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Comics(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var publisher: String? = null
)