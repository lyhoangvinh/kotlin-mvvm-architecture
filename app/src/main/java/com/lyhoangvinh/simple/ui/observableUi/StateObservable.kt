package com.lyhoangvinh.simple.ui.observableUi

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.data.entities.DataEmpty
import javax.inject.Inject

class StateObservable @Inject constructor() : BaseObservable() {

    @Bindable
    var emptyDataOb: DataEmpty? = null

    fun notifyDataEmpty(emptyDataOb: DataEmpty) {
        this.emptyDataOb = emptyDataOb
        notifyPropertyChanged(BR.emptyDataOb)
    }
}