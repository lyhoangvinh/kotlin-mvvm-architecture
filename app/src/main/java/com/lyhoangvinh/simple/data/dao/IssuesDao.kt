package com.lyhoangvinh.simple.data.dao

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.lyhoangvinh.simple.data.entinies.comic.Issues


@Dao
interface IssuesDao : BaseDao<Issues> {
    @Query("SELECT * FROM Issues")
    fun liveData(): LiveData<List<Issues>>

    @Query("SELECT * FROM Issues")
    fun getAllPaged(): DataSource.Factory<Int, Issues>

    @Insert
    fun inserts(list: List<Issues>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(entities: List<Issues>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateIgnore(entities: List<Issues>)

    @Query("DELETE FROM Issues")
    fun removeAll()
}