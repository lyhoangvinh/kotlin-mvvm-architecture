package com.lyhoangvinh.simple.ui.features.avg.main.search.paging

import android.os.Bundle
import android.transition.TransitionInflater
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivitySearchBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelPagingActivity
import com.lyhoangvinh.simple.utils.setStatusBarColor
import com.lyhoangvinh.simple.utils.textChanges

class SearchPagedActivity : BaseViewModelPagingActivity<ActivitySearchBinding, SearchPagedViewModel, SearchPagedAdapter>(){
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