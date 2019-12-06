package com.lyhoangvinh.simple.ui.features.comic.testactivity

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.databinding.ItemComicsBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BasePagedAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.lyhoangvinh.simple.utils.NavigatorHelper
import javax.inject.Inject

class ComicAdapter @Inject constructor(@ActivityContext context: Context, private val navigatorHelper: NavigatorHelper) :
    BasePagedAdapter<Issues, ItemComicsBinding>(context, IssuesDiffCallBack) {

    override fun itemLayoutResource() = R.layout.item_comics

    override fun createViewHolder(itemView: View) = ComicViewHolder(itemView)

    override fun onBindViewHolder(binding: ItemComicsBinding, dto: Issues, position: Int) {
        binding.dto = dto
        binding.onClick = View.OnClickListener {
            navigatorHelper.navigateImageDetailActivity(dto.images)
        }
    }

    class ComicViewHolder(itemView: View) : BaseViewHolder<ItemComicsBinding>(itemView)

    private object IssuesDiffCallBack : DiffUtil.ItemCallback<Issues>() {
        override fun areItemsTheSame(currentItem: Issues, nextItem: Issues): Boolean {
            return currentItem.id == nextItem.id
        }

        override fun areContentsTheSame(currentItem: Issues, nextItem: Issues): Boolean {
            return currentItem == nextItem
        }
    }
}

