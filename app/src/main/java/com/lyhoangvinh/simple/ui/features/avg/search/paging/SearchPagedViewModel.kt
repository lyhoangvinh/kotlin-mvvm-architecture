package com.lyhoangvinh.simple.ui.features.avg.search.paging

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.lyhoangvinh.simple.data.dao.SearchHistoryDao
import com.lyhoangvinh.simple.data.entities.DataEmpty
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.repo.SearchPagedRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.features.avg.search.paging.suggestion.SearchSuggestionsAdapter
import com.lyhoangvinh.simple.ui.observableUi.StateObservable
import javax.inject.Inject

class SearchPagedViewModel @Inject constructor(
    private val searchPagedRepo: SearchPagedRepo,
    private val searchHistoryDao: SearchHistoryDao,
    val stateObservable: StateObservable
) :
    BasePagingViewModel<SearchPagedAdapter>() {

    private var keyword = ""

    private var isFirstState = false

    lateinit var searchSuggestionsAdapter: SearchSuggestionsAdapter

    fun setKeyWord(keyword: String) {
        Handler().postDelayed({
            this.keyword = keyword
            searchPagedRepo.setQuery(keyword)
            isFirstState = true
        }, 500L)
    }

    fun suggestions(keyword: String) {
        execute(
            false,
            searchHistoryDao.search(keyword),
            object : PlainConsumer<List<SearchHistory>> {
                override fun accept(t: List<SearchHistory>) {
                    searchSuggestionsAdapter.submitList(t)
                }
            })
    }

    override fun fetchData() {
        Handler().postDelayed({
            searchPagedRepo.setQuery(keyword)
        }, 500L)
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        searchPagedRepo.rxFetchData().observe(lifecycleOwner, Observer {
            when (it) {
                is StateData -> {
                    if (isFirstState) {
                        adapter.submitState(it.state)
                    } else {
                        isFirstState = true
                    }
                }
                is VideoData -> adapter.submitList(it.videoItems)
                is EmptyMergerdData-> {
                    it.dataEmpty.message = keyword
                    stateObservable.notifyDataEmpty(it.dataEmpty)
                }
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        searchPagedRepo.setQuery("")
    }
}