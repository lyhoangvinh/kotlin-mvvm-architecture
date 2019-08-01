package com.lyhoangvinh.simple.ui.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BaseItemPagerAdapter(diffUtil: DiffUtil.ItemCallback<ItemViewModel>) :
    PagedListAdapter<ItemViewModel, BaseItemViewHolder<ItemViewModel, ViewDataBinding>>(diffUtil) {
    override fun onBindViewHolder(holder: BaseItemViewHolder<ItemViewModel, ViewDataBinding>, position: Int) {
        holder.setItem(getItem(position)!!, holder.binding)
    }
}
