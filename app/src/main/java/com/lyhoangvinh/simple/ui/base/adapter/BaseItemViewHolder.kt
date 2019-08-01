package com.lyhoangvinh.simple.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext

abstract class BaseItemViewHolder<T : ItemViewModel, B : ViewDataBinding>(@ActivityContext val context: Context, parent: ViewGroup, resId: Int) :
    BaseViewHolder<B>(LayoutInflater.from(context).inflate(resId, parent, false)) {

    private lateinit var data: T

    open fun setItem(data: T, binding: B) {
        this.data = data
        this.binding = binding
    }
}