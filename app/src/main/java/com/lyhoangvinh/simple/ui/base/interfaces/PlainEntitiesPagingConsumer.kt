package com.lyhoangvinh.simple.ui.base.interfaces

import com.lyhoangvinh.simple.data.entities.Entities
import io.reactivex.annotations.NonNull

interface PlainEntitiesPagingConsumer<E, T : Entities<E>> {
    fun accept(@NonNull t: List<E>)
}

fun <E, T : Entities<E>> newPlainEntitiesPagingConsumer(consumer: (List<E>) -> Unit) =
    object : PlainEntitiesPagingConsumer<E, T> {
        override fun accept(t: List<E>) {
            consumer.invoke(t)
        }
    }