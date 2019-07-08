package com.lyhoangvinh.simple.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponseComic<T>(
    @SerializedName("error")
    var error: String? = null,

    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null,

    @SerializedName("limit")
    var limit: Int? = null,

    @SerializedName("offset")
    var offset: Int? = 0,

    @SerializedName("number_of_page_results")
    var number_of_page_results: Int? = null,

    @SerializedName("number_of_total_results")
    var number_of_total_results: Int? = null,

    @SerializedName("status_code")
    var status_code: Int? = null,

    @SerializedName("results")
    var results: List<T>
)