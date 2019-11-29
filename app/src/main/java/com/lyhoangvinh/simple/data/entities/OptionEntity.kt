package com.lyhoangvinh.simple.data.entities

data class OptionEntity(
    val index: Int,
    var isCheck: Boolean? = false,
    val name: String
)