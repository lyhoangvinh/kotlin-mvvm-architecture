package com.lyhoangvinh.simple.ui.features.comic.detail

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.entities.BitmapWithQuality
import com.lyhoangvinh.simple.data.entities.comic.ImageAll
import com.lyhoangvinh.simple.data.repo.ImageRepo
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class ImageDetailViewModel @Inject constructor(val repo: ImageRepo) : BaseViewModel() {

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        val imageAll = bundle?.getParcelable<ImageAll>(Constants.EXTRA_DATA)
        if (imageAll != null){
            execute(true, repo.createResource(imageAll))
        }
    }
}