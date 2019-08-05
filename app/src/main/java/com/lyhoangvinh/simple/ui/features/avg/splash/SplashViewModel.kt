package com.lyhoangvinh.simple.ui.features.avg.splash

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.repo.HomeRepo
import com.lyhoangvinh.simple.data.response.*
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.utils.ConnectionLiveData
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
    private val connectionLiveData: ConnectionLiveData
) : BaseViewModel() {

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        connectionLiveData.observe(lifecycleOwner, Observer {
            if (it!!.isConnected) {
                execute(true,
                    homeRepo.getRepoHome(), object :
                        PlainConsumer<ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>> {
                        override fun accept(t: ResponseFourZip<BaseResponseAvgle<CategoriesResponse>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<CollectionsResponseAvgle>, BaseResponseAvgle<VideosResponseAvgle>>) {
                            openHome()
                        }
                    })
            } else {
                openHome()
            }
        })
    }

    private fun openHome() {
        Handler().postDelayed({ navigatorHelper.navigateAvgleActivity() }, 300L)
    }
}