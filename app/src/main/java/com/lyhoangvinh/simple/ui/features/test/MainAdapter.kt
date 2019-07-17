package com.lyhoangvinh.simple.ui.features.test

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import android.view.View
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.databinding.ItemComicsBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BaseAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.lyhoangvinh.simple.utils.BindingUtil
import com.lyhoangvinh.simple.utils.getAppDateFormatter
import com.lyhoangvinh.simple.utils.loadImageIssues
import kotlinx.android.synthetic.main.item_comics.view.*
import javax.inject.Inject

class MainAdapter @Inject constructor(@ActivityContext context: Context) :
    BaseAdapter<Issues, ItemComicsBinding, MainAdapter.MainViewHolder>(context, IssuesDiffCallBack()) {

    override fun itemLayoutResource() = R.layout.item_comics

    override fun createViewHolder(itemView: View) = MainViewHolder(itemView)

    override fun onBindViewHolder(binding: ItemComicsBinding, dto: Issues, position: Int) {
        binding.dto = dto
    }

    class MainViewHolder(itemView: View) : BaseViewHolder<ItemComicsBinding>(itemView)

    class IssuesDiffCallBack : DiffUtil.ItemCallback<Issues>() {
        override fun areItemsTheSame(currentItem: Issues, nextItem: Issues): Boolean {
            return currentItem.id == nextItem.id
        }

        override fun areContentsTheSame(currentItem: Issues, nextItem: Issues): Boolean {
            return currentItem == nextItem
        }
    }
}

