package com.lyhoangvinh.simple.data.entities.avgle

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var keyword: String,

    var url: String,

    var timestamp: String
)