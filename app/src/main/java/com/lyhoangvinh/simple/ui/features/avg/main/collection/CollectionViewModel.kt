package com.lyhoangvinh.simple.ui.features.avg.main.collection

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.CollectionData
import com.lyhoangvinh.simple.data.entities.avgle.StateData
import com.lyhoangvinh.simple.data.repo.CollectionsRepo
import com.lyhoangvinh.simple.data.source.base.BaseRxPagedListDataSource
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import javax.inject.Inject

class CollectionViewModel @Inject constructor(private val collectionsRepo: CollectionsRepo) :
    BasePagingViewModel<CollectionsAdapter>() {

    var title = "Collections all"

    override fun fetchData() {
        publishState(State.success(null))
        collectionsRepo.setUpRepo(mCompositeDisposable)
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        collectionsRepo.setUpRepo(mCompositeDisposable)
        collectionsRepo.fetchData().observe(lifecycleOwner, Observer {
            when (it) {
                is StateData -> adapter.submitState(it.state)
                is CollectionData -> adapter.submitList(it.collections)
            }
        })
    }
}