package com.lyhoangvinh.simple.ui.base.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseViewHolder<B : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var binding: B = DataBindingUtil.bind<B>(itemView)!!
}