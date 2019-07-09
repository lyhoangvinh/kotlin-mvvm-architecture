package com.lyhoangvinh.simple.ui.features.test

import android.support.v7.util.DiffUtil
import android.view.View
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.ui.base.adapter.BaseAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.lyhoangvinh.simple.utils.getAppDateFormatter
import com.lyhoangvinh.simple.utils.loadImageIssues
import kotlinx.android.synthetic.main.item_comics.view.*

class MainAdapter : BaseAdapter<Issues, MainAdapter.MainViewHolder>(IssuesDiffCallBack()) {

    override fun itemLayoutResource() = R.layout.item_comics

    override fun createViewHolder(itemView: View) =  MainViewHolder(itemView)

    override fun onBindViewHolder(vh: MainViewHolder, dto: Issues, position: Int) {
        vh.tvTitle.text = dto.volume.name
        vh.tvTime.text = String.format("Added: %s", getAppDateFormatter(dto.dateAdded!!))
        vh.tvDateLastUpdated.text = String.format("Last updated: %s", getAppDateFormatter(dto.dateLastUpdated!!))
        vh.imv.loadImageIssues(dto.images.medium_url!!)
    }

    class MainViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val tvTitle = itemView.tvTitle!!
        val tvTime = itemView.tvTime!!
        val tvDateLastUpdated = itemView.tvDateLastUpdated!!
        val imv = itemView.imv!!
    }

    class IssuesDiffCallBack : DiffUtil.ItemCallback<Issues>() {
        override fun areItemsTheSame(currentItem: Issues, nextItem: Issues): Boolean {
            return currentItem.id == nextItem.id
        }

        override fun areContentsTheSame(currentItem: Issues, nextItem: Issues): Boolean {
            return currentItem == nextItem
        }
    }
}