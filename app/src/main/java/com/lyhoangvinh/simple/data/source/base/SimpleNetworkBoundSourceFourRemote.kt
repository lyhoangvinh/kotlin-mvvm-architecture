package com.lyhoangvinh.simple.data.source.base


import android.util.Log
import com.lyhoangvinh.simple.data.entities.ErrorEntity
import com.lyhoangvinh.simple.data.response.ResponseFourZip
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
abstract class SimpleNetworkBoundSourceFourRemote<T1, T2, T3, T4>(
    emitter: FlowableEmitter<Resource<ResponseFourZip<T1, T2, T3, T4>>>,
    isRefresh: Boolean
) {

    init {
        emitter.onNext(Resource.loading(null))
        // since realm was create on Main Thread, so if we need to touch on realm database after calling
        // api, must make request on main thread by setting shouldUpdateUi params = true
        makeRequest(this.getRemote1(), this.getRemote2(), this.getRemote3(), this.getRemote4(), true, object :
            PlainResponseFourConsumer<T1, T2, T3, T4> {
            override fun accept(dto: ResponseFourZip<T1, T2, T3, T4>) {
                Log.d(TAG, "SimpleNetworkBoundSource: call API success!")
                saveCallResult(dto, isRefresh)
                emitter.onNext(Resource.success(dto))
            }
        }, object : PlainConsumer<ErrorEntity> {
            override fun accept(t: ErrorEntity) {
                Log.d(TAG, "SimpleNetworkBoundSource: call API error: " + t.getMessage())
                errorResult(t)
                emitter.onNext(Resource.error(t.getMessage(), null))
            }
        })
    }

    abstract fun getRemote1(): Single<T1>

    abstract fun getRemote2(): Single<T2>

    abstract fun getRemote3(): Single<T3>

    abstract fun getRemote4(): Single<T4>

    abstract fun saveCallResult(data: ResponseFourZip<T1, T2, T3, T4>, isRefresh: Boolean)

    abstract fun errorResult(errorEntity: ErrorEntity)

    companion object {
        val TAG = "resource"
    }
}