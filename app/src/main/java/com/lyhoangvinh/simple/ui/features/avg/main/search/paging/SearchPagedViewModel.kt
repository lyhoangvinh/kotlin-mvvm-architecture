package com.lyhoangvinh.simple.ui.features.avg.main.search.paging

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.repo.SearchPagedRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import javax.inject.Inject

class SearchPagedViewModel @Inject constructor(private val searchPagedRepo: SearchPagedRepo) :
    BasePagingViewModel<SearchPagedAdapter>() {

    private var keyword = ""

    private var isFirstState = false

    fun setKeyWord(keyword : String){
        adapter.submitState(State(Status.LOADING, null))
        this.keyword = keyword
        searchPagedRepo.setQuery(keyword)
        isFirstState = true
    }

    override fun fetchData() {
        searchPagedRepo.setQuery(keyword)
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        searchPagedRepo.liveVideo().observe(lifecycleOwner, Observer {
            adapter.submitList(it)
            publishState(State.success(null))
            if(isFirstState){
                isFirstState = false
                adapter.submitState(State(Status.SUCCESS, null))
            }
        })
    }
}