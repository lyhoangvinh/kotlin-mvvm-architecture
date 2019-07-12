package com.lyhoangvinh.simple.ui.features.test

import android.os.Bundle
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.databinding.ViewRecyclerviewBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelRecyclerViewActivity

class MainActivity : BaseViewModelRecyclerViewActivity<ViewRecyclerviewBinding, MainViewModel, MainAdapter, Issues>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshWithUi(300L)
    }
}
