package com.lyhoangvinh.simple.ui.base.adapter

import android.view.View
import androidx.databinding.ViewDataBinding

abstract class BaseItemSimpleViewHolder<T : ItemViewModel, B : ViewDataBinding>(view: View) :
    BaseViewHolder<B>(view) {

    private lateinit var data: T

    open fun setItem(data: T, binding: B) {
        this.data = data
        this.binding = binding
    }
}