package com.lyhoangvinh.simple.data.source.base


import android.util.Log
import com.lyhoangvinh.simple.data.entities.ErrorEntity
import com.lyhoangvinh.simple.data.response.ResponseBiZip
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.utils.makeRequest
import io.reactivex.FlowableEmitter
import io.reactivex.Single
import retrofit2.Response
import java.util.function.BiConsumer

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 */
abstract class SimpleNetworkBoundSourceBiRemote<T1, T2>(
    emitter: FlowableEmitter<Resource<ResponseBiZip<T1, T2>>>,
    isRefresh: Boolean
) {

    init {
        emitter.onNext(Resource.loading(null))
        // since realm was create on Main Thread, so if we need to touch on realm database after calling
        // api, must make request on main thread by setting shouldUpdateUi params = true
        makeRequest(this.getRemote1(), this.getRemote2(), true, object : PlainResponseZipBiConsumer<T1, T2> {
            override fun accept(dto: ResponseBiZip<T1, T2>) {
                Log.d(TAG, "SimpleNetworkBoundSource: call API success!")
                saveCallResult(dto, isRefresh)
                emitter.onNext(Resource.success(dto))
            }
        }, object : PlainConsumer<ErrorEntity> {
            override fun accept(t: ErrorEntity) {
                Log.d(TAG, "SimpleNetworkBoundSource: call API error: " + t.getMessage())
                emitter.onNext(Resource.error(t.getMessage(), null))
            }
        })
    }

    abstract fun getRemote1(): Single<T1>

    abstract fun getRemote2(): Single<T2>

    abstract fun saveCallResult(data: ResponseBiZip<T1, T2>, isRefresh: Boolean)

    companion object {
        val TAG = "resource"
    }
}