package com.lyhoangvinh.simple.data.repo.impl

import android.graphics.Bitmap
import com.lyhoangvinh.simple.data.entities.comic.ImageAll
import com.lyhoangvinh.simple.data.repo.ImageRepo
import com.lyhoangvinh.simple.data.source.base.Resource
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import com.lyhoangvinh.simple.utils.loadProgressively
import com.lyhoangvinh.simple.utils.newPlainConsumer
import com.squareup.picasso.Picasso
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import javax.inject.Inject

class ImageRepoImpl @Inject constructor() : ImageRepo {

    override val bitmapResult by lazy { SafeMutableLiveData<Bitmap>() }

    override fun createResource(imageAll: ImageAll): Flowable<Resource<Bitmap>> =
        Flowable.create({ loadImage(imageAll, it) }, BackpressureStrategy.BUFFER)

    private fun loadImage(imageAll: ImageAll, emitter: FlowableEmitter<Resource<Bitmap>>) {
        emitter.onNext(Resource.loading())
        loadProgressively(Picasso.get(), imageAll, newPlainConsumer {
            bitmapResult.setValue(it)
            emitter.onNext(Resource.success(it))
        }, newPlainConsumer { emitter.onNext(Resource.error(it.getMessage(), null)) })
    }
}