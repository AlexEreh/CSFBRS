package com.alexereh.grades.di

import com.alexereh.database.di.databaseModule
import com.alexereh.grades.GradesRepository
import com.alexereh.grades.GradesRepositoryImpl
import com.alexereh.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val gradesDataModule = module {
    includes(databaseModule, networkModule)
    single<GradesRepository> {
        GradesRepositoryImpl(
            context = androidContext(),
            networkDataSource = get(),
            databaseDataSource = get()
        )
    }
}