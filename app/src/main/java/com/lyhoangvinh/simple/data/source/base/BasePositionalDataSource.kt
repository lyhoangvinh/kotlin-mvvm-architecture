package com.lyhoangvinh.simple.data.source.base

import androidx.paging.PositionalDataSource

abstract class BasePositionalDataSource<T> : PositionalDataSource<T>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {

    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {

    }
}