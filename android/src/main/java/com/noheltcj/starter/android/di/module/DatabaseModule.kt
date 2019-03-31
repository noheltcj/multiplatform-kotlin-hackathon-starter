package com.noheltcj.starter.android.di.module

import android.content.Context
import androidx.room.Room
import com.noheltcj.starter.android.adapter.room.StarterDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(context: Context): StarterDatabase {
        return Room.databaseBuilder(context, StarterDatabase::class.java, "starter-database").build()
    }

    @Provides
    fun providesUserDao(database: StarterDatabase) = database.userDao()
}