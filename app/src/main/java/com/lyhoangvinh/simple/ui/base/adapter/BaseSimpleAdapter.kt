package com.lyhoangvinh.simple.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lyhoangvinh.simple.di.qualifier.ActivityContext

abstract class BaseSimpleAdapter<T, B : ViewDataBinding>(
    @ActivityContext val context: Context,
    diffUtil: DiffUtil.ItemCallback<T>) :
    RecyclerView.Adapter<BaseViewHolder<B>>() {

    private var mDiffer: AsyncListDiffer<T> = AsyncListDiffer(this, diffUtil)

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        val item = getItemAt(position)
        if (item != null) {
            this.onBindViewHolder(holder.binding, item, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        val view = LayoutInflater.from(context).inflate(itemLayoutResource(), parent, false)
        return createViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    private fun getItemAt(position: Int): T? {
        return mDiffer.currentList[position]
    }

    fun submitList(itemList: List<T>) {
        mDiffer.submitList(itemList)
    }

    abstract fun itemLayoutResource(): Int

    abstract fun createViewHolder(itemView: View): BaseViewHolder<B>

    abstract fun onBindViewHolder(binding: B, dto: T, position: Int)
}
