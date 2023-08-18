package com.alexereh.csfbrs.di

import com.alexereh.datastore.di.dataStoreModule
import com.alexereh.grades.di.gradesDataModule
import org.koin.dsl.module

val appModule = module {
    includes(dataStoreModule, gradesDataModule)
}