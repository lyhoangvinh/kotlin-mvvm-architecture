package com.lyhoangvinh.simple.ui.features.comicavg

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.dao.CategoriesDao
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.ui.widget.recycleview.RecyclerTabLayout
import javax.inject.Inject

class ComicAvgViewModel @Inject constructor(private val categoriesDao: CategoriesDao) :
    BaseViewModel() {
    private var adapter: ComicAvgPagerAdapter? = null
    private var tabAdapter: RecyclerTabLayout.CustomTabAdapter? = null
    fun initAdapter(adapter: ComicAvgPagerAdapter, tabAdapter: RecyclerTabLayout.CustomTabAdapter) {
        this.adapter = adapter
        this.tabAdapter = tabAdapter
    }

    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {
        categoriesDao.liveData().observe(lifecycleOwner, Observer {
            tabAdapter?.updateData(0, arrayListOf<String>().apply {
                for (i in 0 until it.size) {
                    add(it[i].name.toString())
                }
            })
            adapter?.submitData(it)
        })
    }
}