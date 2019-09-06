package com.lyhoangvinh.simple.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lyhoangvinh.simple.data.dao.*
import com.lyhoangvinh.simple.data.entities.avgle.Category
import com.lyhoangvinh.simple.data.entities.avgle.Collection
import com.lyhoangvinh.simple.data.entities.avgle.SearchHistory
import com.lyhoangvinh.simple.data.entities.avgle.Video
import com.lyhoangvinh.simple.data.entities.comic.Comics
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.typecoverter.ImageTypeConverter
import com.lyhoangvinh.simple.data.typecoverter.VolumeTypeConverter

@Database(
    entities = [Comics::class, Issues::class, Category::class, Collection::class, Video::class, SearchHistory::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ImageTypeConverter::class, VolumeTypeConverter::class)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun issuesDao(): IssuesDao

    abstract fun categoriesDao(): CategoriesDao

    abstract fun collectionDao(): CollectionDao

    abstract fun videosDao(): VideosDao

    abstract fun searchHistoryDao(): SearchHistoryDao
}
