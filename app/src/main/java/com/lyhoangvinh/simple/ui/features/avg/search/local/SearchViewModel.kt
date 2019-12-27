package com.lyhoangvinh.simple.ui.features.avg.search.local

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.repo.SearchRepo
import com.lyhoangvinh.simple.data.response.BaseResponseAvgle
import com.lyhoangvinh.simple.data.response.ResponseBiZip
import com.lyhoangvinh.simple.data.response.VideosResponseAvgle
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseListDataViewModel
import com.lyhoangvinh.simple.utils.newPlainConsumer
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchRepo: SearchRepo) :
    BaseListDataViewModel<SearchAdapter>() {

    private var keyword = ""

    fun setKeyWord(keyword: String) {
        this.keyword = keyword
        fetchData(0)
    }

    override fun fetchData(page: Int) {
        execute(true, searchRepo.search(isRefreshed, keyword, page), newPlainConsumer {
            isRefreshed = false
            canLoadMore = it.t1?.response?.hasMore!!
        })
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        searchRepo.mergedData().observe(lifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}