package com.lyhoangvinh.simple.ui.features.comicavg

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.lyhoangvinh.simple.data.dao.CategoriesDao
import com.lyhoangvinh.simple.ui.base.viewmodel.BaseViewModel
import com.lyhoangvinh.simple.ui.widget.recycleview.RecyclerTabLayout
import javax.inject.Inject

class ComicAvgViewModel @Inject constructor(val categoriesDao: CategoriesDao) :
    BaseViewModel() {
    override fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?) {

    }
}