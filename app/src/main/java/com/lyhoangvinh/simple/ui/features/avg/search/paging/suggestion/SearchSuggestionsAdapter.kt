package com.lyhoangvinh.simple.ui.features.avg.search.paging.suggestion

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.avgle.SearchHistory
import com.lyhoangvinh.simple.databinding.ItemHistoryBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BaseSimpleAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.lyhoangvinh.simple.utils.NavigatorHelper
import javax.inject.Inject

class SearchSuggestionsAdapter @Inject constructor(@ActivityContext context: Context, private val navigatorHelper: NavigatorHelper) :
    BaseSimpleAdapter<SearchHistory, ItemHistoryBinding>(context, ItemCallBack) {
    override fun itemLayoutResource() = R.layout.item_history
    override fun createViewHolder(itemView: View) = SearchSuggestionsViewHolder(itemView)
    override fun onBindViewHolder(binding: ItemHistoryBinding, dto: SearchHistory, position: Int) {
        binding.dto = dto
        binding.navigate = navigatorHelper
    }

    class SearchSuggestionsViewHolder(itemView: View) : BaseViewHolder<ItemHistoryBinding>(itemView)
    private object ItemCallBack : DiffUtil.ItemCallback<SearchHistory>() {
        override fun areItemsTheSame(currentItem: SearchHistory, nextItem: SearchHistory): Boolean {
            return currentItem.keyword == nextItem.keyword
        }

        override fun areContentsTheSame(
            currentItem: SearchHistory,
            nextItem: SearchHistory
        ): Boolean {
            return currentItem == nextItem
        }
    }
}