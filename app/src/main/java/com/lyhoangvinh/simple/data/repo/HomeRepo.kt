package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import com.lyhoangvinh.simple.data.repo.impl.HomeCommonData
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel

interface HomeRepo {
    fun fetchData(): LiveData<List<ItemViewModel>>
    fun liveDataHome(): LiveData<List<ItemViewModel>>
    fun getRepoHome(): HomeCommonData
}
