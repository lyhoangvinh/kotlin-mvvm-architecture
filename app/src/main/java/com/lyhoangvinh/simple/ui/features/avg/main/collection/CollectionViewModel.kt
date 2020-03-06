package com.lyhoangvinh.simple.ui.features.avg.main.collection

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.entities.State
import com.lyhoangvinh.simple.data.entities.avgle.CollectionData
import com.lyhoangvinh.simple.data.entities.avgle.StateData
import com.lyhoangvinh.simple.data.repo.CollectionsRepo
import com.lyhoangvinh.simple.ui.base.viewmodel.BasePagingViewModel
import com.lyhoangvinh.simple.ui.observableUi.StateObservable
import com.lyhoangvinh.simple.utils.SafeMutableLiveData
import javax.inject.Inject

class CollectionViewModel @Inject constructor(private val collectionsRepo: CollectionsRepo) :
    BasePagingViewModel<CollectionsAdapter>() {

    var title = "Collections all"

    var isFirst = false

    override fun fetchData() {
        collectionsRepo.invalidateDataSource()
        publishState(State.success())
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        collectionsRepo.rxFetchData(mCompositeDisposable).observe(lifecycleOwner, Observer {
            when (it) {
                is StateData -> {
                    if (isFirst) {
                        adapter.submitState(it.state)
                    } else {
                        isFirst = true
                    }
                    publishState(it.state)
                }
                is CollectionData -> adapter.submitList(it.collections)
            }
        })
    }
}