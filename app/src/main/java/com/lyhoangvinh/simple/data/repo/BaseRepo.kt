package com.lyhoangvinh.simple.data.repo

import com.lyhoangvinh.simple.data.entities.ErrorEntity
import com.lyhoangvinh.simple.data.response.ResponseBiZip
import com.lyhoangvinh.simple.data.response.ResponseFourZip
import com.lyhoangvinh.simple.data.source.base.*
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


abstract class BaseRepo {

    /**
     * For single data
     * @param remote
     * @param onSave
     * @param <T>
     * @return
    </T> */
    fun <T> createResource(
        remote: Single<T>,
        onSave: PlainConsumer<T>
    ): Flowable<Resource<T>> {
        return Flowable.create({
            object : SimpleNetworkBoundSource<T>(it, true) {
                override fun getRemote() = remote
                override fun saveCallResult(data: T, isRefresh: Boolean) {
                    onSave.accept(data)
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    /**
     * For a list of data
     * @param isRefresh
     * @param remote
     * @param onSave
     * @param <T>
     * @return
    </T> */
    fun <T> createResource(
        isRefresh: Boolean,
        remote: Single<T>,
        onSave: OnSaveResultListener<T>
    ): Flowable<Resource<T>> {
        return Flowable.create({
            object : SimpleNetworkBoundSource<T>(it, isRefresh) {
                override fun getRemote() = remote
                override fun saveCallResult(data: T, isRefresh: Boolean) {
                    onSave.onSave(data, isRefresh)
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    /**
     * For 2 single data
     * @param remote1
     * @param remote2
     * @param onSave
     * @param <T>
     * @return
    </T> */
    fun <T1, T2> createResource(
        remote1: Single<T1>,
        remote2: Single<T2>,
        onSave: PlainResponseZipBiConsumer<T1, T2>
    ): Flowable<Resource<ResponseBiZip<T1, T2>>> {
        return Flowable.create({
            object : SimpleNetworkBoundSourceBiRemote<T1, T2>(it, true) {
                override fun getRemote1() = remote1
                override fun getRemote2() = remote2
                override fun saveCallResult(data: ResponseBiZip<T1, T2>, isRefresh: Boolean) {
                    onSave.accept(data)
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    fun <T1, T2> createResource(
        isRefresh: Boolean,
        remote1: Single<T1>,
        remote2: Single<T2>,
        onSave: OnSaveBiResultListener<T1, T2>
    ): Flowable<Resource<ResponseBiZip<T1, T2>>> {
        return Flowable.create({
            object : SimpleNetworkBoundSourceBiRemote<T1, T2>(it, isRefresh) {
                override fun getRemote1() = remote1
                override fun getRemote2() = remote2
                override fun saveCallResult(data: ResponseBiZip<T1, T2>, isRefresh: Boolean) {
                    onSave.onSave(data, isRefresh)
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    interface OnSaveBiResultListener<T1, T2> {
        fun onSave(data: ResponseBiZip<T1, T2>, isRefresh: Boolean)
    }

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
        onSave: PlainResponseZipFourConsumer<T1, T2, T3, T4>,
        onError: PlainConsumer<ErrorEntity>
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
                override fun errorResult(errorEntity: ErrorEntity) {
                    onError.accept(errorEntity)
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    /**
     * Excute room
    </T> */

    fun execute(action: () -> Unit) {
        Completable.fromAction {
            action.invoke()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    interface OnSaveResultListener<T> {
        fun onSave(data: T, isRefresh: Boolean)
    }


}


