package com.lyhoangvinh.simple.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.lyhoangvinh.simple.data.entities.avgle.SearchHistory

@Dao
interface SearchHistoryDao : BaseDao<SearchHistory> {
    @Query("SELECT * FROM SearchHistory")
    fun liveDataFactory(): DataSource.Factory<Int, SearchHistory>
}