package com.lyhoangvinh.simple.ui.base.interfaces

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer

interface PlainConsumer<T> : Consumer<T> {
    override fun accept(@NonNull t: T)
}
