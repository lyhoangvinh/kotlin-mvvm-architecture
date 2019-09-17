package com.lyhoangvinh.simple.ui.features.avg.search.paging

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.databinding.ItemDataSearchBinding
import com.lyhoangvinh.simple.databinding.ItemSearchBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BasePagedAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.lyhoangvinh.simple.utils.NavigatorHelper
import com.lyhoangvinh.simple.utils.genericCastOrNull
import javax.inject.Inject

class SearchPagedAdapter @Inject constructor(@ActivityContext context: Context, private val navigatorHelper: NavigatorHelper) :
    BasePagedAdapter<Video, ItemDataSearchBinding>(context, ItemCallBack) {
    override fun itemLayoutResource() = R.layout.item_data_search
    override fun createViewHolder(itemView: View) : BaseViewHolder<ItemDataSearchBinding> = genericCastOrNull(SearchPagedViewHolder(itemView))
    override fun onBindViewHolder(binding: ItemDataSearchBinding, dto: Video, position: Int) {
        binding.navigate = navigatorHelper
        binding.dto = dto
    }
    class SearchPagedViewHolder(itemView: View) : BaseViewHolder<ItemSearchBinding>(itemView)
    private object ItemCallBack : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(currentItem: Video, nextItem: Video): Boolean {
            return currentItem.uid == nextItem.uid
        }

        override fun areContentsTheSame(currentItem: Video, nextItem: Video): Boolean {
            return currentItem == nextItem
        }
    }
}