package com.lyhoangvinh.simple.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.lyhoangvinh.simple.data.entities.avgle.Category

@Dao
interface CategoriesDao : BaseDao<Category> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(entities: List<Category>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateIgnore(entities: List<Category>)

    @Query("DELETE FROM CATEGORY")
    fun deleteAll()

    @Query("SELECT * FROM CATEGORY")
    fun liveData(): LiveData<List<Category>>

    @Query("SELECT * FROM CATEGORY")
    fun getData() : List<Category>

    @Query("SELECT * FROM CATEGORY")
    fun liveDataFactory(): DataSource.Factory<Int, Category>

}