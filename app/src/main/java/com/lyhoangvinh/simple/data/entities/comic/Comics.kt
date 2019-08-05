package com.lyhoangvinh.simple.data.entities.comic

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comics(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var publisher: String? = null
)