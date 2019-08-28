package com.lyhoangvinh.simple.ui.features.avg.main.search

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.itemviewmodel.*
import com.lyhoangvinh.simple.databinding.ItemDataSearchBinding
import com.lyhoangvinh.simple.databinding.ItemHistoryBinding
import com.lyhoangvinh.simple.databinding.ItemVideoBinding
import com.lyhoangvinh.simple.databinding.ViewRcyHorizontalBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BaseItemSimpleAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseItemSimpleViewHolder
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import com.lyhoangvinh.simple.ui.features.avg.main.home.adapter.inside.VideosHomeAdapter
import com.lyhoangvinh.simple.ui.widget.recycleview.GravitySnapHelper
import com.lyhoangvinh.simple.ui.widget.recycleview.HorizontalSpaceItemDecoration
import com.lyhoangvinh.simple.utils.NavigatorHelper
import com.lyhoangvinh.simple.utils.genericCastOrNull
import javax.inject.Inject

class SearchAdapter @Inject constructor(@ActivityContext context: Context, val navigatorHelper: NavigatorHelper) :
    BaseItemSimpleAdapter(context, ItemCallback) {

    companion object {
        private const val ITEM_HISTORY = 0
        private const val ITEM_DATA = 1
    }

    override fun setItemViewType(item: ItemViewModel): Int {
        return when (item) {
            is SearchHistoryItem -> ITEM_HISTORY
            is SearchDataItem -> ITEM_DATA
            else -> throw RuntimeException("Not support item $item")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            ITEM_HISTORY -> R.layout.item_history
            ITEM_DATA -> R.layout.item_data_search
            else -> throw RuntimeException("Not support layoutResource $viewType")
        }
    }

    override fun createItemViewHolder(view: View, viewType: Int): BaseItemSimpleViewHolder<ItemViewModel, ViewDataBinding> {
        return when(viewType){
            ITEM_HISTORY -> genericCastOrNull(SearchHistoryViewHolder(view, navigatorHelper))
            ITEM_DATA -> genericCastOrNull(DataSearchViewHolder(view, navigatorHelper))
            else -> throw RuntimeException("Not support type=$viewType")
        }
    }

    private class DataSearchViewHolder(view: View, private val navigatorHelper: NavigatorHelper) :
        BaseItemSimpleViewHolder<SearchDataItem, ItemDataSearchBinding>(view) {
        override fun setItem(data: SearchDataItem, binding: ItemDataSearchBinding) {
            super.setItem(data, binding)
            binding.dto = data.video
            binding.navigate = navigatorHelper
        }
    }

    private class SearchHistoryViewHolder(view: View, private val navigatorHelper: NavigatorHelper)
        : BaseItemSimpleViewHolder<SearchHistoryItem, ItemHistoryBinding>(view){
        override fun setItem(data: SearchHistoryItem, binding: ItemHistoryBinding) {
            super.setItem(data, binding)
            binding.dto = data.data
            binding.navigate = navigatorHelper
        }
    }

    private object ItemCallback : DiffUtil.ItemCallback<ItemViewModel>() {
        override fun areItemsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {
                oldItem is SearchHistoryItem && newItem is SearchHistoryItem -> oldItem.idViewModel == newItem.idViewModel
                oldItem is SearchDataItem && newItem is SearchDataItem -> oldItem.idViewModel == newItem.idViewModel
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {
                oldItem is SearchHistoryItem && newItem is SearchHistoryItem -> oldItem.data == newItem.data
                oldItem is SearchDataItem && newItem is SearchDataItem -> oldItem.video == newItem.video
                else -> false
            }
        }
    }
}