package com.lyhoangvinh.simple.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.utils.compare

abstract class BaseSimpleAdapter<T, B : ViewDataBinding>(@ActivityContext val context: Context, diffUtil: DiffUtil.ItemCallback<T>) : ListAdapter<T, BaseViewHolder<B>>(diffUtil) {

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        val item = getItemAt(position)
        if (item != null) {
            this.onBindViewHolder(holder.binding, item, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> = createViewHolder(LayoutInflater.from(context).inflate(itemLayoutResource(), parent, false))

    abstract fun itemLayoutResource(): Int

    abstract fun createViewHolder(itemView: View): BaseViewHolder<B>

    abstract fun onBindViewHolder(binding: B, dto: T, position: Int)

    fun getData() : List<T> = this.currentList

    fun getItemAt(position: Int): T? = getItem(position)

    fun submitListIfNew(newList: List<T>){
        if (!getData().compare(newList)) submitList(newList)
    }
}
