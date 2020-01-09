package com.lyhoangvinh.simple.ui.features.setting

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.SharedPrefs
import com.lyhoangvinh.simple.data.entities.OptionEntity
import com.lyhoangvinh.simple.databinding.ItemSettingBinding
import com.lyhoangvinh.simple.di.qualifier.ActivityContext
import com.lyhoangvinh.simple.ui.base.adapter.BaseSimpleAdapter
import com.lyhoangvinh.simple.ui.base.adapter.BaseViewHolder
import javax.inject.Inject

class SettingAdapter @Inject constructor(@ActivityContext context: Context, private val sharedPrefs: SharedPrefs) :
    BaseSimpleAdapter<OptionEntity, ItemSettingBinding>(context, ItemCallBack) {
    override fun itemLayoutResource() = R.layout.item_setting
    override fun createViewHolder(itemView: View) = SettingViewHolder(itemView)
    override fun onBindViewHolder(binding: ItemSettingBinding, dto: OptionEntity, position: Int) {
        binding.setting = dto
        binding.setOnClick { sharedPrefs.put(Constants.OPTIONS, position) }
    }

    class SettingViewHolder(itemView: View) : BaseViewHolder<ItemSettingBinding>(itemView)
    private object ItemCallBack : DiffUtil.ItemCallback<OptionEntity>() {
        override fun areItemsTheSame(currentItem: OptionEntity, nextItem: OptionEntity): Boolean {
            return currentItem.isCheck == nextItem.isCheck
        }

        override fun areContentsTheSame(currentItem: OptionEntity, nextItem: OptionEntity): Boolean {
            return currentItem == nextItem
        }
    }
}