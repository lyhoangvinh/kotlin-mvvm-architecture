package com.lyhoangvinh.simple.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.lyhoangvinh.simple.data.dao.IssuesDao
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.typecoverter.ImageTypeConverter
import com.lyhoangvinh.simple.data.typecoverter.VolumeTypeConverter


@Database(
    entities = [Issues::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ImageTypeConverter::class, VolumeTypeConverter::class)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun issuesDao(): IssuesDao
}
