package com.lyhoangvinh.simple.ui.observableUi

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.lyhoangvinh.simple.BR
import com.lyhoangvinh.simple.data.entities.DataEmpty
import com.lyhoangvinh.simple.data.entities.VisibilityView
import javax.inject.Inject

class StateObservable @Inject constructor() : BaseObservable() {

    @Bindable
    var emptyDataOb: DataEmpty? = null

    fun notifyDataEmpty(emptyDataOb: DataEmpty) {
        this.emptyDataOb = emptyDataOb
        notifyPropertyChanged(BR.emptyDataOb)
    }

    @Bindable
    var isShowClearText: Boolean? = false

    fun notifyShowClearText(isShowClearText: Boolean) {
        this.isShowClearText = isShowClearText
        notifyPropertyChanged(BR.isShowClearText)
    }

    @Bindable
    var visibilityView: VisibilityView? = null

    fun notifyShowSuggestionView(visibilityView: VisibilityView) {
        this.visibilityView = visibilityView
        notifyPropertyChanged(BR.visibilityView)
    }
}