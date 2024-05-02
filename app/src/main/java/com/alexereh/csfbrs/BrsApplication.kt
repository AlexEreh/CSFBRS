package com.alexereh.csfbrs

import android.app.Application
import com.alexereh.csfbrs.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class BrsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BrsApplication)
            modules(appModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}