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

abstract class BaseItemSimpleAdapter(
    @ActivityContext val context: Context,
    diffUtil: DiffUtil.ItemCallback<ItemViewModel>) :
    RecyclerView.Adapter<BaseItemSimpleViewHolder<ItemViewModel, ViewDataBinding>>() {

    private var mDiffer: AsyncListDiffer<ItemViewModel> =  AsyncListDiffer(this, diffUtil)

    override fun getItemViewType(position: Int): Int {
        return setItemViewType(getItemAt(position)!!)
    }

    override fun onBindViewHolder(holder: BaseItemSimpleViewHolder<ItemViewModel, ViewDataBinding>, position: Int) {
        holder.setItem(mDiffer.currentList[position], holder.binding)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseItemSimpleViewHolder<ItemViewModel, ViewDataBinding> {
        val view = LayoutInflater.from(context).inflate(getLayoutResource(viewType), parent, false)
        return createItemViewHolder(view, viewType)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    fun getItemAt(position: Int): ItemViewModel? {
        return mDiffer.currentList[position]
    }

    fun submitList(itemList: List<ItemViewModel>) {
        mDiffer.submitList(itemList)
    }

    abstract fun setItemViewType(item: ItemViewModel): Int

    abstract fun getLayoutResource(viewType: Int): Int

    abstract fun createItemViewHolder(
        view: View,
        viewType: Int
    ): BaseItemSimpleViewHolder<ItemViewModel, ViewDataBinding>

}
