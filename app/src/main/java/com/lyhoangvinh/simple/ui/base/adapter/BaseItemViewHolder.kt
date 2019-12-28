package com.lyhoangvinh.simple.ui.base.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.utils.inflate

abstract class BaseItemViewHolder<T : ItemViewModel, B : ViewDataBinding>(@ActivityContext val context: Context, parent: ViewGroup, resId: Int) :
    BaseViewHolder<B>(parent.inflate(context, resId)) {

    private lateinit var data: T

    open fun setItem(data: T, binding: B) {
        this.data = data
        this.binding = binding
    }
}