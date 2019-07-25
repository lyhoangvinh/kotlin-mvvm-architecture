package com.lyhoangvinh.simple.data.source

import androidx.paging.PositionalDataSource
import com.lyhoangvinh.simple.data.response.ResponseFourZip
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single

abstract class BasePositionalDataSource<T> : PositionalDataSource<T>() {
    /**
     * For 4 single data
     * @param remote1
     * @param remote2
     * @param remote3
     * @param remote4
     * @param onSave
     * @param <T>
     * @return
    </T> */

    fun <T1, T2, T3, T4> createResource(
        remote1: Single<T1>,
        remote2: Single<T2>,
        remote3: Single<T3>,
        remote4: Single<T4>,
        onSave: PlainResponseZipFourConsumer<T1, T2, T3, T4>
    ): Flowable<Resource<ResponseFourZip<T1, T2, T3, T4>>> {
        return Flowable.create({
            object : SimpleNetworkBoundSourceFourRemote<T1, T2, T3, T4>(it, true) {
                override fun getRemote1() = remote1
                override fun getRemote2() = remote2
                override fun getRemote3() = remote3
                override fun getRemote4() = remote4
                override fun saveCallResult(data: ResponseFourZip<T1, T2, T3, T4>, isRefresh: Boolean) {
                    onSave.accept(data)
                }

            }
        }, BackpressureStrategy.BUFFER)
    }
}