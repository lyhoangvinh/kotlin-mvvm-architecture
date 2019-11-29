package com.lyhoangvinh.simple.ui.features.avg.splash

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.Constants
import com.lyhoangvinh.simple.data.SharedPrefs
import com.lyhoangvinh.simple.data.repo.HomeRepo
import com.lyhoangvinh.simple.data.response.*
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.utils.ConnectionLiveData
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
                    execute(true,
                        homeRepo.getRepoHome(), object :
                            PlainConsumer<ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>> {
                            override fun accept(t: ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>) {
                                when (options) {
                                    Constants.OPTIONS_2 -> openHome()
                                    Constants.OPTIONS_3 -> openComicAvg()
                                }
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