package com.noheltcj.starter.android.adapter.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noheltcj.starter.android.adapter.entity.PersistableUser
import com.noheltcj.starter.android.adapter.room.dao.UserDao

@Database(
    entities = [
        PersistableUser::class
    ],
    version = 1,
    exportSchema = false
)
abstract class StarterDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}