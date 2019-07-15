package com.lyhoangvinh.simple.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.lyhoangvinh.simple.MyApplication
import com.lyhoangvinh.simple.data.DatabaseManager
import com.lyhoangvinh.simple.data.SharedPrefs
import com.lyhoangvinh.simple.data.dao.IssuesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(context: MyApplication): DatabaseManager {
        return Room.databaseBuilder(context, DatabaseManager::class.java, "my-database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    internal fun providesSharePeres(context: MyApplication): SharedPrefs = SharedPrefs.getInstance(context)

    @Provides
    @Singleton
    fun provideIssuesDao(databaseManager: DatabaseManager): IssuesDao = databaseManager.issuesDao()

}