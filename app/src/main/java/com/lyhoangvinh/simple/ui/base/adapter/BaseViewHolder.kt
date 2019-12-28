package com.lyhoangvinh.simple.ui.base.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<B : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var binding: B = DataBindingUtil.bind(itemView)!!
}