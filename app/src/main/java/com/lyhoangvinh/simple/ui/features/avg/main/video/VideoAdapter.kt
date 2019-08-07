package com.lyhoangvinh.simple.ui.features.avg.main.video

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.databinding.ItemVideoBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BaseAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.lyhoangvinh.simple.utils.NavigatorHelper
import javax.inject.Inject

class VideoAdapter @Inject constructor(@ActivityContext context: Context, private val navigatorHelper: NavigatorHelper) :
    BaseAdapter<Video, ItemVideoBinding, VideoAdapter.VideoViewHolder>(context, ItemCallBack) {
    override fun itemLayoutResource() = R.layout.item_video
    override fun createViewHolder(itemView: View) = VideoViewHolder(itemView)
    override fun onBindViewHolder(binding: ItemVideoBinding, dto: Video, position: Int) {
        binding.dto = dto
        binding.tvPreview.setOnClickListener { navigatorHelper.navigateDetailActivity(dto.previewVideoUrl.toString()) }
        binding.lnlRootWallet.setOnClickListener { navigatorHelper.navigateDetailActivity(dto.previewUrl.toString()) }
    }
    class VideoViewHolder(itemView: View) : BaseViewHolder<ItemVideoBinding>(itemView)
    private object ItemCallBack : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(currentItem: Video, nextItem: Video): Boolean {
            return currentItem.uid == nextItem.uid
        }
        override fun areContentsTheSame(currentItem: Video, nextItem: Video): Boolean {
            return currentItem == nextItem
        }
    }
}