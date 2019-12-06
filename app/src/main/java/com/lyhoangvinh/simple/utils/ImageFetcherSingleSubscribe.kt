package com.lyhoangvinh.simple.utils

import com.lyhoangvinh.simple.data.entities.BitmapWithQuality
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import lyhoangvinh.com.myutil.thread.UIThreadExecutor


class ImageFetcherSingleSubscribe(
    private val picasso: Picasso,
    private val url: String
) : SingleOnSubscribe<BitmapWithQuality> {

    private val runningTargets = mutableListOf<Target>()

    override fun subscribe(emitter: SingleEmitter<BitmapWithQuality>) {
        val target = CustomImageLoadTarget(emitter) {
            removeTargetAndCancelRequest(it)
        }
        UIThreadExecutor.getInstance().runOnUIThread {
            runningTargets.add(target)
            picasso.load(url)
                .into(target)
        }
    }

    private fun removeTargetAndCancelRequest(target: Target) {
        UIThreadExecutor.getInstance().runOnUIThread {
            runningTargets.remove(target)
            picasso.cancelRequest(target)
        }

    }
}
