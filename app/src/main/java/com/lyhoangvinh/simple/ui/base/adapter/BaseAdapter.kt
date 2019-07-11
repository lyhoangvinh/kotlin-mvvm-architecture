package com.lyhoangvinh.simple.ui.base.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lyhoangvinh.simple.di.qualifier.ActivityContext

abstract class BaseAdapter<T, VH : BaseViewHolder>(@ActivityContext val context: Context, diffUtil: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, VH>(diffUtil) {

    abstract fun itemLayoutResource(): Int

    abstract fun createViewHolder(itemView: View): VH

    protected abstract fun onBindViewHolder(vh: VH, dto: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        this.createViewHolder(LayoutInflater.from(context).inflate(itemLayoutResource(), parent, false))

    override fun onBindViewHolder(vh: VH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            this.onBindViewHolder(vh, item, position)
        }
    }
}