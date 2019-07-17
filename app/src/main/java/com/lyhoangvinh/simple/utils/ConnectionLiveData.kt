package com.lyhoangvinh.simple.utils

import androidx.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.lyhoangvinh.simple.data.entinies.Connection
import com.lyhoangvinh.simple.receiver.NetworkReceiver


@Suppress("DEPRECATION")
class ConnectionLiveData(private val context: Context) : LiveData<Connection>() {

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReceiver)
    }

    private val networkReceiver = object : NetworkReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.extras != null) {
                val activeNetwork = intent.extras!!.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo
                val isConnected = activeNetwork.isConnectedOrConnecting
                if (isConnected) {
                    when (activeNetwork.type) {
                        ConnectivityManager.TYPE_WIFI -> postValue(
                            Connection(
                                CONNECTION_WIFI,
                                true
                            )
                        )
                        ConnectivityManager.TYPE_MOBILE -> postValue(
                            Connection(
                                CONNECTION_MOBILE,
                                true
                            )
                        )
                    }
                } else {
                    postValue(
                        Connection(
                            CONNECTION_NONE,
                            false
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val CONNECTION_NONE = 0
        private const val CONNECTION_WIFI = 1
        private const val CONNECTION_MOBILE = 2
    }
}