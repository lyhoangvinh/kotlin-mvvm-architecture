package com.lyhoangvinh.simple.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
