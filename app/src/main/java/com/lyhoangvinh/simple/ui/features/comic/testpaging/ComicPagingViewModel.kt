package com.lyhoangvinh.simple.ui.features.comic.testpaging

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.repo.IssuesRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import com.lyhoangvinh.simple.ui.features.comic.testactivity.ComicAdapter
import javax.inject.Inject

class ComicPagingViewModel @Inject constructor(private val issuesRepo: IssuesRepo) :
    BasePagingViewModel<ComicAdapter>() {

    override fun fetchData() {
        issuesRepo.invalidate()
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
//        issuesRepo.livePagingData(setStateLiveData, mCompositeDisposable).observe(lifecycleOwner, Observer {
//            adapter.submitList(it)
//        })
        issuesRepo.liveLocalPagingData(stateLiveData, mCompositeDisposable).observe(lifecycleOwner, Observer {
            adapter.submitList(it)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        issuesRepo.clear()
    }
}