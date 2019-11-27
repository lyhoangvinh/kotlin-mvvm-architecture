package com.lyhoangvinh.simple.ui.features.comicavg

import android.os.Bundle
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ActivityComicAvgBinding
import com.lyhoangvinh.simple.ui.base.activity.BaseViewModelActivity

class ComicAvgActivity : BaseViewModelActivity<ActivityComicAvgBinding, ComicAvgViewModel>(){
    override fun getLayoutResource() = R.layout.activity_comic_avg
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}