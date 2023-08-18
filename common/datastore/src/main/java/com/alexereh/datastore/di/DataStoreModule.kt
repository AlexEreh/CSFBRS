package com.alexereh.datastore.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.alexereh.datastore.UserDataSerializer
import com.alexereh.datastore.UserDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    includes(cryptoModule)
    single {
        DataStoreFactory.create(
            serializer = get<UserDataSerializer>(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            migrations = listOf()
        ) {
            androidContext().dataStoreFile("user_data.pb")
        }
    }
    single {
        UserDataSerializer(get())
    }
    single {
        UserDataSource(get())
    }
}