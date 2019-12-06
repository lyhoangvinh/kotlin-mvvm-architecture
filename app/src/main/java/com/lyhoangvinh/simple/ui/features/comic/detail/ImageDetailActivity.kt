package com.lyhoangvinh.simple.ui.features.comic.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivityImageDetailBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity

class ImageDetailActivity :
    BaseViewModelActivity<ActivityImageDetailBinding, ImageDetailViewModel>() {
    override fun getLayoutResource() = R.layout.activity_image_detail
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.repo.bitmapResult.observe(this, Observer {
            binding.imv.setImageBitmap(it.bitmap)
        })
    }
}