package com.lyhoangvinh.simple.ui.base.interfaces

interface PaginationListener {
    var isRefreshed : Boolean

    var canLoadMore : Boolean

    var currentPage : Int
}