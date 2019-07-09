package com.lyhoangvinh.simple.ui.base.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>(diffUtil: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, VH>(diffUtil) {

    abstract fun itemLayoutResource(): Int

    abstract fun createViewHolder(itemView: View): VH

    protected abstract fun onBindViewHolder(vh: VH, dto: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        this.createViewHolder(LayoutInflater.from(parent.context).inflate(itemLayoutResource(), parent, false))

    override fun onBindViewHolder(vh: VH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            this.onBindViewHolder(vh, item, position)
        }
    }
}