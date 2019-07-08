package com.lyhoangvinh.simple.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.lyhoangvinh.simple.data.dao.IssuesDao
import com.lyhoangvinh.simple.data.entinies.Issues


@Database(
    entities = [Issues::class],
    version = 1,
    exportSchema = false
)

abstract class DatabaseManager : RoomDatabase() {
    abstract fun issuesDao(): IssuesDao
}
