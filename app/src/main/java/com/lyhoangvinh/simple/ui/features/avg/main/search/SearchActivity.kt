package com.lyhoangvinh.simple.ui.features.avg.main.search

import android.os.Bundle
import android.text.TextUtils
import android.transition.TransitionInflater
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivitySearchBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelRecyclerViewActivity
import com.lyhoangvinh.simple.utils.setStatusBarColor
import com.lyhoangvinh.simple.utils.textChanges

class SearchActivity :
    BaseViewModelRecyclerViewActivity<ActivitySearchBinding, SearchViewModel, SearchAdapter>() {
    override fun getLayoutResource() = R.layout.activity_search
    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBarColor(R.color.colorWhite)
        window.enterTransition =
            TransitionInflater.from(this)
                .inflateTransition(R.transition.changebounds_with_arcmotion)
        super.onCreate(savedInstanceState)
        binding.edtSearch.textChanges {
            viewModel.setKeyWord(it)
        }
        binding.imvBack.setOnClickListener { onBackPressed() }
    }
}