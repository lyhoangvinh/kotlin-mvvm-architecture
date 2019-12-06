package com.lyhoangvinh.simple.data.source.base

import androidx.annotation.NonNull
import com.lyhoangvinh.simple.data.response.ResponseBiZip


interface PlainResponseBiConsumer<T1, T2> {
    fun accept(@NonNull dto: ResponseBiZip<T1, T2>)
}