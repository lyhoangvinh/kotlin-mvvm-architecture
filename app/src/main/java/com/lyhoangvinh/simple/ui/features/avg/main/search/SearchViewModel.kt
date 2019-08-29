package com.lyhoangvinh.simple.ui.features.avg.main.search

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseListDataViewModel<SearchAdapter>() {

    private val keyword = ""

    override fun fetchData(page: Int) {

    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {

    }
}