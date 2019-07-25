package com.lyhoangvinh.simple.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lyhoangvinh.simple.data.dao.CategoriesDao
import com.lyhoangvinh.simple.data.dao.CollectionDao
import com.lyhoangvinh.simple.data.dao.IssuesDao
import com.lyhoangvinh.simple.data.dao.VideosDao
import com.lyhoangvinh.simple.data.entinies.avgle.Category
import com.lyhoangvinh.simple.data.entinies.avgle.Collection
import com.lyhoangvinh.simple.data.entinies.avgle.Video
import com.lyhoangvinh.simple.data.entinies.comic.Comics
import com.lyhoangvinh.simple.data.entinies.comic.Issues
import com.lyhoangvinh.simple.data.typecoverter.ImageTypeConverter
import com.lyhoangvinh.simple.data.typecoverter.VolumeTypeConverter

@Database(
    entities = [Comics::class, Issues::class, Category::class, Collection::class, Video::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ImageTypeConverter::class, VolumeTypeConverter::class)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun issuesDao(): IssuesDao

    abstract fun categoriesDao(): CategoriesDao

    abstract fun collectionDao(): CollectionDao

    abstract fun videosDao(): VideosDao

}
