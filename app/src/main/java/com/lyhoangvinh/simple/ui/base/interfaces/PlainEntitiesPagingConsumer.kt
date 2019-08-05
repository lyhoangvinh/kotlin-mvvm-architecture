package com.lyhoangvinh.simple.ui.base.interfaces

import com.lyhoangvinh.simple.data.entities.Entities
import io.reactivex.annotations.NonNull

interface PlainEntitiesPagingConsumer<E,T : Entities<E>> {
    fun accept(@NonNull t: List<E>)
}