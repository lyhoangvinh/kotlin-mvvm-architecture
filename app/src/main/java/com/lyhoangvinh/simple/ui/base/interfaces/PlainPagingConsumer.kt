package com.lyhoangvinh.simple.ui.base.interfaces

import io.reactivex.annotations.NonNull

interface PlainPagingConsumer<T> {
    fun accept(@NonNull t: List<T>)
}

fun<T> newPlainPagingConsumer(consumer: (List<T>)-> Unit)= object : PlainPagingConsumer<T>{
    override fun accept(t: List<T>) {
         consumer.invoke(t)
    }
}