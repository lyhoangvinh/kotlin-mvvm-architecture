package com.lyhoangvinh.simple.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.lyhoangvinh.simple.data.entities.avgle.Collection

@Dao
interface CollectionDao : BaseDao<Collection> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(entities: List<Collection>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateIgnore(entities: List<Collection>)

    @Query("DELETE FROM COLLECTION")
    fun deleteAll()

    @Query("SELECT * FROM COLLECTION")
    fun liveData(): LiveData<List<Collection>>

    @Query("SELECT * FROM COLLECTION WHERE type=:type")
    fun liveDataFromType(type: Int): LiveData<List<Collection>>

    @Query("DELETE FROM COLLECTION WHERE type=:value")
    fun deleteType(value: Int)

    @Query("SELECT * FROM COLLECTION WHERE type=:type")
    fun liveDataFactoryFromType(type: Int): DataSource.Factory<Int, Collection>
}