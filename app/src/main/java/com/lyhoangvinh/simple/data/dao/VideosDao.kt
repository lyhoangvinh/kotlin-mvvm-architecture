package com.lyhoangvinh.simple.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.lyhoangvinh.simple.data.entities.avgle.Video

@Dao
interface VideosDao : BaseDao<Video> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(entities: List<Video>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateIgnore(entities: List<Video>)

    @Query("DELETE FROM VIDEO WHERE type=:value")
    fun deleteType(value: Int)

    @Query("DELETE FROM VIDEO")
    fun deleteAll()

    @Query("SELECT * FROM VIDEO")
    fun liveData(): LiveData<List<Video>>

    @Query("SELECT * FROM VIDEO WHERE type=:value")
    fun liveDataFromType(value: Int): LiveData<List<Video>>

    @Query("SELECT * FROM VIDEO WHERE type=:type")
    fun liveDataFactoryFromType(type: Int): DataSource.Factory<Int, Video>
}