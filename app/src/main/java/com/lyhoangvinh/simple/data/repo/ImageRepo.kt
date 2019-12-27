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

class ImageRepo @Inject constructor() {

    val bitmapResult = SafeMutableLiveData<Bitmap>()

    fun createResource(imageAll: ImageAll): Flowable<Resource<Bitmap>> =
        Flowable.create({ loadImage(imageAll, it) }, BackpressureStrategy.BUFFER)

    private fun loadImage(imageAll: ImageAll, emitter: FlowableEmitter<Resource<Bitmap>>) {
        emitter.onNext(Resource.loading(null))
        loadProgressively(Picasso.get(), imageAll, newPlainConsumer {
            bitmapResult.setValue(it)
            emitter.onNext(Resource.success(it))
        }, newPlainConsumer { emitter.onNext(Resource.error(it.getMessage(), null)) })
    }
}