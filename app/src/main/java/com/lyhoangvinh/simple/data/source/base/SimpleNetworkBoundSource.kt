package com.lyhoangvinh.simple.data.source.base


import android.util.Log
import com.lyhoangvinh.simple.data.entities.ErrorEntity
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.utils.makeRequest
import io.reactivex.FlowableEmitter
import io.reactivex.Single

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 */
abstract class SimpleNetworkBoundSource<T>(emitter: FlowableEmitter<Resource<T>>, isRefresh: Boolean) {

    init {
        emitter.onNext(Resource.loading(null))
        // since realm was create on Main Thread, so if we need to touch on realm database after calling
        // api, must make request on main thread by setting shouldUpdateUi params = true
        makeRequest(this.getRemote(), true, object : PlainConsumer<T> {
            override fun accept(t: T) {
                Log.d(TAG, "SimpleNetworkBoundSource: call API success!")
                saveCallResult(t, isRefresh)
                emitter.onNext(Resource.success(t))
            }
        }, object : PlainConsumer<ErrorEntity> {
            override fun accept(t: ErrorEntity) {
                Log.d(TAG, "SimpleNetworkBoundSource: call API error: " + t.getMessage())
                emitter.onNext(Resource.error(t.getMessage(), null))
            }
        })
    }

    abstract fun getRemote(): Single<T>

    abstract fun saveCallResult(data: T, isRefresh: Boolean)

    companion object {
        const val TAG = "resource"
    }
}