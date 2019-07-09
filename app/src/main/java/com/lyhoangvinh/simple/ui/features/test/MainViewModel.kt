package com.lyhoangvinh.simple.ui.features.test

import android.os.Bundle
import com.lyhoangvinh.simple.data.source.State
import com.lyhoangvinh.simple.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    var text: String = ""

    fun clickOK() {
        text = "ABC"
        publishState(State.success(null))
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?) {
        disposeAllExecutions()
    }

}