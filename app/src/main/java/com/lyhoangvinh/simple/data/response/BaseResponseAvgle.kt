package com.lyhoangvinh.simple.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponseAvgle<T>(

    @SerializedName("success")
    @Expose
    var success: Boolean,

    @SerializedName("response")
    @Expose
    var response: T

)