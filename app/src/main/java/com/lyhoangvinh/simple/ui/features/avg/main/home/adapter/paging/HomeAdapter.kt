package com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.data.itemviewmodel.*
import com.lyhoangvinh.simple.databinding.ItemCategoriesBinding
import com.lyhoangvinh.simple.databinding.ItemSearchBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.*
import javax.inject.Inject

class HomeAdapter @Inject constructor(@ActivityContext val context: Context) : BaseItemPagerAdapter(ItemCallback) {

    companion object {
        private const val ITEM_LOADING = 0
        private const val ITEM_SEARCH = 1
        private const val ITEM_CATEGORY = 2
        private const val ITEM_BANNER = 3
        private const val ITEM_VIDEO = 4
        private const val ITEM_COLLECTION_BOTTOM = 5
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchItem -> ITEM_SEARCH
            is CategoryItem -> ITEM_CATEGORY
            is VideoItem -> ITEM_VIDEO
            is CollectionBannerItem -> ITEM_BANNER
            is CollectionBottomItem -> ITEM_COLLECTION_BOTTOM
            else -> ITEM_LOADING // loading model
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder<ItemViewModel, ViewDataBinding> {
        when (viewType) {
            ITEM_SEARCH -> SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search, parent, false))

            else -> LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false))
        }
        throw RuntimeException("Not support type=$viewType")
    }

    private object ItemCallback : DiffUtil.ItemCallback<ItemViewModel>() {
        override fun areItemsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {

                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {

                else -> false
            }
        }
    }

    private class SearchViewHolder(itemView: View) : BaseViewHolder<ItemSearchBinding>(itemView)

    private class LoadingViewHolder(itemView: View) : BaseViewHolder<ItemSearchBinding>(itemView)


    private class CategoryAdapter(context: Context) :
        BasePagedAdapter<Category, ItemCategoriesBinding>(
            context,
            CategoryDiffCallBack
        ) {
        override fun itemLayoutResource() = R.layout.item_categories
        override fun createViewHolder(itemView: View) =
            CategoryItemViewHolder(
                itemView
            )

        override fun onBindViewHolder(binding: ItemCategoriesBinding, dto: Category, position: Int) {
            binding.dto = dto
        }

        private class CategoryItemViewHolder(itemView: View) : BaseViewHolder<ItemCategoriesBinding>(itemView)

        private object CategoryDiffCallBack : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(currentItem: Category, nextItem: Category): Boolean {
                return currentItem.id == nextItem.id
            }

            override fun areContentsTheSame(currentItem: Category, nextItem: Category): Boolean {
                return currentItem == nextItem
            }
        }
    }
}