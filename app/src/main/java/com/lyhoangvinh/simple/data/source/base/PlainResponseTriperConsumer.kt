package com.lyhoangvinh.simple.data.source.base

import androidx.annotation.NonNull
import com.lyhoangvinh.simple.data.response.ResponseFourZip
import com.lyhoangvinh.simple.data.response.ResponseTriper


interface PlainResponseTriperConsumer<T1, T2, T3> {
    fun accept(@NonNull dto: ResponseTriper<T1, T2, T3>)
}