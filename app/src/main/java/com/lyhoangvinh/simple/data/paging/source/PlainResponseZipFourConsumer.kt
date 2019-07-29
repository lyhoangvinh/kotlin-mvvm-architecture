package com.lyhoangvinh.simple.data.paging.source

import androidx.annotation.NonNull
import com.lyhoangvinh.simple.data.response.ResponseFourZip


interface PlainResponseZipFourConsumer<T1, T2, T3, T4> {
    fun accept(@NonNull dto: ResponseFourZip<T1, T2, T3, T4>)
}