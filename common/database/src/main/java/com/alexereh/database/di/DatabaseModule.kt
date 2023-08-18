package com.alexereh.database.di

import androidx.room.Room
import com.alexereh.database.DatabaseDataSource
import com.alexereh.database.room.GradesDatabase
import com.alexereh.database.room.RoomDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            GradesDatabase::class.java,
            "grades"
        ).build()
    }

    single<DatabaseDataSource> {
        RoomDataSource(get())
    }
}