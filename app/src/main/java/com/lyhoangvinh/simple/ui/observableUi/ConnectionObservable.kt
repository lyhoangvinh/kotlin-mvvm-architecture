package com.lyhoangvinh.simple.ui.observableUi

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.data.entities.Connection
import com.lyhoangvinh.simple.utils.ConnectionLiveData
import javax.inject.Inject

class ConnectionObservable @Inject constructor(private val connectionLive: ConnectionLiveData) :
    BaseObservable() {

    @Bindable
    var currentConnection: Connection? = null

    fun observableConnection(lifecycleOwner: LifecycleOwner) {
        connectionLive.observe(lifecycleOwner, Observer {
            currentConnection = it
            notifyPropertyChanged(BR.currentConnection)
        })
    }
}