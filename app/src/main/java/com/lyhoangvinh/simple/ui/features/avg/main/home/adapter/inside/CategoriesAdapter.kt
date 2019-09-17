package com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.inside

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.databinding.ItemCategoriesBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BasePagedAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder

class CategoriesAdapter(@ActivityContext context: Context) :
    BasePagedAdapter<Category, ItemCategoriesBinding>(
        context,
        CategoryDiffCallBack
    ) {

    override fun itemLayoutResource() = R.layout.item_categories

    override fun createViewHolder(itemView: View) = CategoriesViewHolder(itemView)

    private var onClickItemListener: ((Category) -> Unit?)? = null

    fun setOnClickItemListener(onClickItemListener: (Category) -> Unit): CategoriesAdapter {
        this.onClickItemListener = onClickItemListener
        return this
    }

    override fun onBindViewHolder(binding: ItemCategoriesBinding, dto: Category, position: Int) {
        binding.dto = dto
        binding.rlRoot.setOnClickListener { onClickItemListener?.invoke(dto) }
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