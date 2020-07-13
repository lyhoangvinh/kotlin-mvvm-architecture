package com.lyhoangvinh.simple.data.repo

import android.graphics.Bitmap
import com.lyhoangvinh.simple.data.entities.ErrorEntity
import com.lyhoangvinh.simple.data.entities.comic.ImageAll
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.loadProgressively
import com.lyhoangvinh.simple.utils.newPlainConsumer
import com.squareup.picasso.Picasso
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import javax.inject.Inject

interface ImageRepo {
    fun createResource(imageAll: ImageAll): Flowable<Resource<Bitmap>>
    val bitmapResult : SafeMutableLiveData<Bitmap>
}

