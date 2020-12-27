package com.lyhoangvinh.simple.utils.rx

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lyhoangvinh.simple.utils.SingletonHolder
import com.lyhoangvinh.simple.utils.rx.rxlifecycle.AndroidLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.ConcurrentHashMap

class RxBus private constructor(any: Any) {
    private var bus: Subject<Any>? = null
    private var stickyEventMap: ConcurrentHashMap<Class<*>, Any>? = null

    companion object : SingletonHolder<RxBus, Any>(::RxBus)

    init {
        bus = PublishSubject.create<Any>().toSerialized()
        stickyEventMap = ConcurrentHashMap()
    }

    /**
     * Broadcast event
     */
    fun post(event: Any?) {
        event?.let { bus?.onNext(it) }
    }

    fun <T> toObservable(
        owner: LifecycleOwner?,
        eventType: Class<T>?
    ): LiveData<T>? {
        return toObservable(owner, eventType, Lifecycle.Event.ON_DESTROY)
    }

    /**
     * Make use of Rx-lifecycle to solve memory leaking issue
     */
    fun <T> toObservable(
        owner: LifecycleOwner?,
        eventType: Class<T>?,
        lifecycleEvent: Lifecycle.Event
    ): LiveData<T>? {
        val provider = AndroidLifecycle.createLifecycleProvider(owner)
        return bus?.ofType(eventType)
            ?.doOnDispose { Log.i("RXBUS","Unsubscribe from RxBus") }
            ?.compose(provider.bindUntilEvent(lifecycleEvent))
            ?.subscribeOn(Schedulers.io())
            ?.unsubscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.toLiveData()
    }

    fun <T> toObservable(eventType: Class<T>?): LiveData<T> {
        return toObservable2(eventType)
    }

    private fun <T> toObservable2(eventType: Class<T>?): LiveData<T> {
        return bus!!.ofType(eventType)
            .doOnDispose { Log.i("RXBUS","Unsubscribe from RxBus") }
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toLiveData()
    }

    private fun <T> Observable<T>.toLiveData(): LiveData<T> {

        return object : MutableLiveData<T>() {
            var disposable: Disposable? = null;

            override fun onActive() {
                super.onActive()
                // Observable -> LiveData
                disposable = this@toLiveData.subscribe {
                    try {
                        this.setValue(it)
                    } catch (e: Exception) {
                        // if we can't set value due to not in main thread, must call post value instead
                        this.postValue(it)
                    }
                }
            }

            override fun onInactive() {
                disposable?.dispose();
                super.onInactive()
            }
        }
    }

    /**
     * To determine whether there's a observer subscribed to current Bus
     */
    fun hasObservers(): Boolean {
        return bus?.hasObservers() ?: false
    }

    fun reset() {
//        defaultInstance = null
    }

    /**
     * Sticky event related

     * Broadcast a sticky type event
     */
    fun postSticky(event: Any) {
        stickyEventMap?.let {
            synchronized(it) {
                stickyEventMap?.put(event.javaClass, event)
            }
        }
        post(event)
    }


    fun <T> toObservableSticky(owner: LifecycleOwner?, eventType: Class<T>?): LiveData<T>? {
        return toObservableSticky(owner, eventType!!, Lifecycle.Event.ON_DESTROY)
    }

    /**
     * Make use of Rx-lifecycle to solve memory leaking issue
     */
    fun <T> toObservableSticky(
        owner: LifecycleOwner?,
        eventType: Class<T>,
        lifecycleEvent: Lifecycle.Event
    ): LiveData<T>? {
        return stickyEventMap?.let {
            synchronized(it) {
                val provider =
                    AndroidLifecycle.createLifecycleProvider(owner)
                val observable = bus?.ofType(eventType)
                    ?.doOnDispose { Log.i("RXBUS","Unsubscribe from RxBus") }
                    ?.compose(provider.bindUntilEvent(lifecycleEvent))
                    ?.subscribeOn(Schedulers.io())
                    ?.unsubscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                val event = stickyEventMap?.get(eventType)
                if (event != null) {
                    observable?.mergeWith(Observable.create { subscriber ->
                        subscriber.onNext(eventType.cast(event)!!)
                    })
                } else {
                    observable
                }
            }?.toLiveData()
        }
    }

    /**
     * Get sticky event based on eventType
     */
    fun <T> getStickyEvent(eventType: Class<T>): T? {
        return stickyEventMap?.let {
            synchronized(it) {
                eventType.cast(stickyEventMap?.get(eventType))
            }
        }
    }

    /**
     * Remove sticky event based on eventType
     */
    fun <T> removeStickyEvent(eventType: Class<T>): T? {
        return stickyEventMap?.let {
            synchronized(it) {
                eventType.cast(
                    stickyEventMap?.remove(
                        eventType
                    )
                )
            }
        }
    }

    /**
     * Removed all sticky events
     */
    fun removeAllStickyEvents() {
        stickyEventMap?.let { synchronized(it) { stickyEventMap?.clear() } }
    }
}