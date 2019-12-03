package com.lyhoangvinh.simple.ui.features.comicavg.portal

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.databinding.ItemPortalBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BasePagedAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.lyhoangvinh.simple.utils.NavigatorHelper
import javax.inject.Inject

class PortalAdapter @Inject constructor(@ActivityContext context: Context, private val navigatorHelper: NavigatorHelper) :
    BasePagedAdapter<Video, ItemPortalBinding>(context, ItemCallBack) {
    override fun itemLayoutResource() = R.layout.item_portal
    override fun createViewHolder(itemView: View) = PortalViewHolder(itemView)
    override fun onBindViewHolder(binding: ItemPortalBinding, dto: Video, position: Int) {
        binding.dto = dto
        binding.onClick = View.OnClickListener {
            navigatorHelper.navigateDetailActivity(dto.embeddedUrl.toString())
        }
    }

    class PortalViewHolder(itemView: View) : BaseViewHolder<ItemPortalBinding>(itemView)
    private object ItemCallBack : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(currentItem: Video, nextItem: Video): Boolean {
            return currentItem.uid == nextItem.uid
        }

        override fun areContentsTheSame(
            currentItem: Video,
            nextItem: Video
        ): Boolean {
            return currentItem == nextItem
        }
    }
}