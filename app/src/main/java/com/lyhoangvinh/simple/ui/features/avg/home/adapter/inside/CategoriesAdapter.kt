package com.lyhoangvinh.simple.ui.features.avg.home.adapter.inside

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entinies.avgle.Category
import com.lyhoangvinh.simple.databinding.ItemCategoriesBinding
import com.lyhoangvinh.simple.ui.base.adapter.BaseAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.lyhoangvinh.simple.utils.loadImage

class CategoriesAdapter(context: Context) : BaseAdapter<Category, ItemCategoriesBinding, CategoriesAdapter.CategoriesViewHolder>(context,CategoryDiffCallBack) {

    override fun itemLayoutResource() = R.layout.item_categories

    override fun createViewHolder(itemView: View) = CategoriesViewHolder(itemView)

    private var onClickItemListener: ((Category) -> Unit?)? = null

    fun setOnClickItemListener(onClickItemListener: (Category) -> Unit): CategoriesAdapter {
        this.onClickItemListener = onClickItemListener
        return this
    }

    override fun onBindViewHolder(binding: ItemCategoriesBinding, dto: Category, position: Int) {
        binding.tvName.text = dto.name
        binding.imv.loadImage(dto.coverUrl.toString())
    }

    class CategoriesViewHolder(itemView: View) : BaseViewHolder<ItemCategoriesBinding>(itemView)

    private object CategoryDiffCallBack : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(currentItem: Category, nextItem: Category): Boolean {
            return currentItem.id == nextItem.id
        }

        override fun areContentsTheSame(currentItem: Category, nextItem: Category): Boolean {
            return currentItem == nextItem
        }
    }
}