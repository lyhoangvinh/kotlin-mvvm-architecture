package com.lyhoangvinh.simple.ui.features.avg.search.paging

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.paging.PagedList
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.data.entities.avgle.SearchHistory
import com.lyhoangvinh.simple.databinding.ActivitySearchBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelPagingActivity
import com.lyhoangvinh.simple.ui.features.avg.search.paging.suggestion.SearchSuggestionsAdapter
import com.lyhoangvinh.simple.utils.hideKeyboard
import com.lyhoangvinh.simple.utils.setStatusBarColor
import com.lyhoangvinh.simple.utils.textChanges
import javax.inject.Inject

class SearchPagedActivity :
    BaseViewModelPagingActivity<ActivitySearchBinding, SearchPagedViewModel, SearchPagedAdapter>() {

    @Inject
    lateinit var searchSuggestionsAdapter: SearchSuggestionsAdapter

    override fun getLayoutResource() = R.layout.activity_search
    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBarColor(R.color.colorWhite)
        window.enterTransition =
            TransitionInflater.from(this)
                .inflateTransition(R.transition.changebounds_with_arcmotion)
        super.onCreate(savedInstanceState)
        viewModel.searchSuggestionsAdapter = searchSuggestionsAdapter
        binding.rcvHistorySearch.adapter = searchSuggestionsAdapter
        binding.vm = viewModel
        binding.edtSearch.textChanges {
            viewModel.suggestions(it)
        }
        binding.imvBack.setOnClickListener { onBackPressed() }
    }

}