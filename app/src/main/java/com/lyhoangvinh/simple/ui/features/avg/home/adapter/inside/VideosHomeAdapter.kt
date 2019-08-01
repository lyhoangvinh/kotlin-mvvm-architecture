package com.lyhoangvinh.simple.ui.features.avg.home.adapter.inside

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entinies.avgle.Video
import com.lyhoangvinh.simple.databinding.ItemVideoHomeBinding
import com.lyhoangvinh.simple.ui.base.adapter.BaseAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder


class VideosHomeAdapter(context: Context) :
    BaseAdapter<Video, ItemVideoHomeBinding, VideosHomeAdapter.VideoViewHolder>(context, DiffCallBack) {


    private var onItemClickListener: ((String) -> Unit)? = null

    private var mWidth = 0

    private var mHeight = 0

    fun setLayoutParams(mWidth: Int, mHeight: Int): VideosHomeAdapter {
        this.mWidth = mWidth
        this.mHeight = mHeight
        return this
    }

    fun setOnItemClickListener(onItemClickListener: (String) -> Unit): VideosHomeAdapter {
        this.onItemClickListener = onItemClickListener
        return this
    }

    override fun itemLayoutResource() = R.layout.item_video_home

    override fun createViewHolder(itemView: View) = VideoViewHolder(itemView)

    override fun onBindViewHolder(binding: ItemVideoHomeBinding, dto: Video, position: Int) {
        binding.imv.layoutParams.width = mWidth
        binding.imv.layoutParams.height = mHeight
        binding.lnlMain.layoutParams = RelativeLayout.LayoutParams(mWidth, RelativeLayout.LayoutParams.WRAP_CONTENT)
        binding.imv.requestLayout()
    }

    class VideoViewHolder(itemView: View) : BaseViewHolder<ItemVideoHomeBinding>(itemView)

    private object DiffCallBack : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(currentItem: Video, nextItem: Video): Boolean {
            return currentItem.vid == nextItem.vid
        }

        override fun areContentsTheSame(currentItem: Video, nextItem: Video): Boolean {
            return currentItem == nextItem
        }
    }
}
