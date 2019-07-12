package com.lyhoangvinh.simple.ui.base.interfaces

interface UiRefreshable : Refreshable {
    fun doneRefresh()
    fun refreshWithUi()
    fun refreshWithUi(delay: Long)
}
