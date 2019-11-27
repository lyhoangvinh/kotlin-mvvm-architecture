package com.lyhoangvinh.simple.ui.features.avg.search.paging

import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.dao.SearchHistoryDao
import com.lyhoangvinh.simple.data.entities.VisibilityView
import com.lyhoangvinh.simple.data.entities.avgle.*
import com.lyhoangvinh.simple.data.repo.SearchPagedRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import com.lyhoangvinh.simple.ui.base.interfaces.PlainConsumer
import com.lyhoangvinh.simple.ui.base.interfaces.OnClickable
import com.lyhoangvinh.simple.ui.features.avg.search.paging.suggestion.SearchSuggestionsAdapter
import com.lyhoangvinh.simple.ui.observableUi.StateObservable
import javax.inject.Inject

class SearchPagedViewModel @Inject constructor(
    private val searchPagedRepo: SearchPagedRepo,
    private val searchHistoryDao: SearchHistoryDao,
    val stateObservable: StateObservable
) :
    BasePagingViewModel<SearchPagedAdapter>() {

    var keyword = ""

    var searchClickable = object : OnClickable {
        override fun accept() {
            setKeyWord()
        }
    }

    var onClearTextClickable = object : OnClickable {
        override fun accept() {
            keyword = ""
            stateObservable.notifyShowClearText(false)
        }
    }

    var searchSuggestionsListener = object : OnClickable{
        override fun accept() {
            if (!TextUtils.isEmpty(keyword)){
                suggestions(keyword)
            }
            stateObservable.notifyShowClearText(keyword.isNotEmpty())
        }
    }

    private var isFirstState = false

    lateinit var searchSuggestionsAdapter: SearchSuggestionsAdapter

    fun setKeyWord() {
        if (!TextUtils.isEmpty(keyword)) {
            searchPagedRepo.setQuery(keyword)
            isFirstState = true
        }
    }

    fun suggestions(keyword: String) {
        execute(
            false,
            searchHistoryDao.search(keyword),
            object : PlainConsumer<List<SearchHistory>> {
                override fun accept(t: List<SearchHistory>) {
                    if (t.isNullOrEmpty()) {
                        searchSuggestionsAdapter.submitList(t)
                    }
                    stateObservable.notifyShowSuggestionView(VisibilityView(!t.isNullOrEmpty()))
                }
            })
    }

    override fun fetchData() {
        searchPagedRepo.setQuery(keyword)
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        searchPagedRepo.rxFetchData(mCompositeDisposable).observe(lifecycleOwner, Observer {
            when (it) {
                is StateData -> {
                    if (isFirstState) {
                        adapter.submitState(it.state)
                    } else {
                        isFirstState = true
                    }
                }
                is VideoData -> adapter.submitList(it.videoItems)
                is EmptyMergerdData -> {
                    stateObservable.notifyDataEmpty(it.dataEmpty.apply {
                        message = keyword
                    })
                }
            }
        })
    }
}