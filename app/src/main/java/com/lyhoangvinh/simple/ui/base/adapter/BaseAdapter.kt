package com.lyhoangvinh.simple.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.di.qualifier.ActivityContext

abstract class BaseAdapter<T, B : ViewDataBinding, VH : BaseViewHolder<B>>(
    @ActivityContext val context: Context, diffUtil: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, VH>(diffUtil) {

    abstract fun itemLayoutResource(): Int

    abstract fun createViewHolder(itemView: View): VH

    protected abstract fun onBindViewHolder(binding: B, dto: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        this.createViewHolder(LayoutInflater.from(context).inflate(itemLayoutResource(), parent, false))

    override fun onBindViewHolder(vh: VH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            this.onBindViewHolder(vh.binding, item, position)
        }
    }
}