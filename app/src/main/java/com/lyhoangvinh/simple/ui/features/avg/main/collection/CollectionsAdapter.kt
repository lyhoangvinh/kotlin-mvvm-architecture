package com.lyhoangvinh.simple.ui.features.avg.main.collection

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ItemCollectionBinding
import com.lyhoangvinh.simple.ui.base.adapter.BaseAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import com.lyhoangvinh.simple.utils.NavigatorHelper
import javax.inject.Inject
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.di.qualifier.ActivityContext

class CollectionsAdapter @Inject constructor(@ActivityContext context: Context, private var navigatorHelper: NavigatorHelper) :
    BaseAdapter<Collection, ItemCollectionBinding>(context,ItemCallBack) {
    override fun itemLayoutResource() = R.layout.item_collection
    override fun createViewHolder(itemView: View) = CollectionViewHolder(itemView)
    override fun onBindViewHolder(binding: ItemCollectionBinding, dto: Collection, position: Int) {
        binding.dto = dto
        binding.rlRoot.setOnClickListener { navigatorHelper.navigateVideosFragment(dto) }
    }

    class CollectionViewHolder(itemView: View) : BaseViewHolder<ItemCollectionBinding>(itemView)
    private object ItemCallBack : DiffUtil.ItemCallback<Collection>() {
        override fun areItemsTheSame(currentItem: Collection, nextItem: Collection): Boolean {
            return currentItem.idCLS == nextItem.idCLS
        }
        override fun areContentsTheSame(currentItem: Collection, nextItem: Collection): Boolean {
            return currentItem == nextItem
        }
    }
}