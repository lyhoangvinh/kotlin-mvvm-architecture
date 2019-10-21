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
import com.lyhoangvinh.simple.data.entities.avgle.SearchHistory
import com.lyhoangvinh.simple.data.entities.avgle.Video
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

    private lateinit var observer: Observer<PagedList<Video>?>

    lateinit var searchSuggestionsAdapter: SearchSuggestionsAdapter

    fun setKeyWord(keyword: String) {
        adapter.submitState(State(Status.LOADING, null))
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
        adapter.submitState(State(Status.LOADING, null))
        Handler().postDelayed({
            searchPagedRepo.setQuery(keyword)
        }, 500L)
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        observer = Observer {
            adapter.submitList(it)
            publishState(State.success(null))
            if (isFirstState) {
                isFirstState = false
                adapter.submitState(State(Status.SUCCESS, null))
                searchPagedRepo.insertHistory(query = keyword)
            }
            Log.d("Activity_SEARCHXXXX_XXX", "list: ${adapter.currentList?.size}")
        }
        searchPagedRepo.liveVideo().observe(lifecycleOwner, observer)
    }

    override fun onCleared() {
        super.onCleared()
        searchPagedRepo.setQuery("")
        searchPagedRepo.liveVideo().removeObserver(observer)
    }
}