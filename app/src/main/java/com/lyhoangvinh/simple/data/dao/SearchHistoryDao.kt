package com.lyhoangvinh.simple.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.lyhoangvinh.simple.data.entities.avgle.SearchHistory
import io.reactivex.Single

@Dao
interface SearchHistoryDao : BaseDao<SearchHistory> {
    @Query("SELECT * FROM SearchHistory")
    fun liveDataFactory(): DataSource.Factory<Int, SearchHistory>

    @Query("SELECT * FROM SearchHistory WHERE keyword LIKE :query ")
    fun search(query: String) : Single<List<SearchHistory>>
}