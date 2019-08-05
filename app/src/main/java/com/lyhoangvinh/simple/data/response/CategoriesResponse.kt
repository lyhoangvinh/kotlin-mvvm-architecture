package com.lyhoangvinh.simple.data.response

 import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
 import com.lyhoangvinh.simple.data.entities.avgle.Category

data class CategoriesResponse(

    @SerializedName("categories")
    @Expose
    var categories: List<Category>? = ArrayList()

)