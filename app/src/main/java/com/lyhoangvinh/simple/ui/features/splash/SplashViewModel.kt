package com.lyhoangvinh.simple.ui.features.splash

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.SharedPrefs
import com.lyhoangvinh.simple.data.repo.HomeRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.utils.ConnectionLiveData
import com.lyhoangvinh.simple.utils.newPlainConsumer
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
    private val connectionLiveData: ConnectionLiveData,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        val options = sharedPrefs[Constants.OPTIONS, Int::class.java]
        if (options == Constants.OPTIONS_1) {
            openComic()
        } else {
            connectionLiveData.observe(lifecycleOwner, Observer {
                if (it.isConnected) {
                    execute(true, homeRepo.getRepoHome(), newPlainConsumer {
                            when (options) {
                                Constants.OPTIONS_2 -> openHome()
                                Constants.OPTIONS_3 -> openComicAvg()
                            }
                        })
                }
            })
        }
    }

    private fun openHome() {
        navigatorHelper.navigateAvgleActivity()
    }

    private fun openComic() {
        navigatorHelper.navigateIssusActivity()
    }

    private fun openComicAvg() {
        navigatorHelper.navigateComicAvgActivity()
    }
}