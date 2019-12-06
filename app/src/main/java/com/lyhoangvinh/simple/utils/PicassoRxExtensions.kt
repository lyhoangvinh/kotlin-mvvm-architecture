package com.lyhoangvinh.simple.utils

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.lyhoangvinh.simple.data.entities.BitmapWithQuality
import com.lyhoangvinh.simple.data.entities.ErrorEntity
import com.lyhoangvinh.simple.data.entities.comic.ImageAll
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

import io.reactivex.schedulers.Schedulers

fun loadProgressively(
    picasso: Picasso,
    imageAll: ImageAll, @NonNull responseConsumer: PlainConsumer<BitmapWithQuality>,
    @Nullable errorConsumer: PlainConsumer<ErrorEntity>?
) = makeProgressively(
        loadImageAndIgnoreError(picasso, imageAll.thumbUrl.toString()),
        loadImageAndIgnoreError(picasso, imageAll.medium_url.toString()),
        loadImageAndIgnoreError(picasso, imageAll.superUrl.toString()),
        responseConsumer,
        errorConsumer
    )

/**
 * Make 3 Request
 */
fun makeProgressively(
    request1: Single<BitmapWithQuality>,
    request2: Single<BitmapWithQuality>,
    request3: Single<BitmapWithQuality>,
    @NonNull responseConsumer: PlainConsumer<BitmapWithQuality>,
    @Nullable errorConsumer: PlainConsumer<ErrorEntity>?
): Disposable {
    return Single.merge(request1, request2, request3)
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .subscribe({ o ->
            responseConsumer.accept(o)
        }, { throwable ->
            // handle error
//                throwable.printStackTrace()
            errorConsumer?.accept(ErrorEntity.getError(throwable))
        })
}

fun loadImageAndIgnoreError(picasso: Picasso, url: String): Single<BitmapWithQuality> =
    Single.create(ImageFetcherSingleSubscribe(picasso, url))

