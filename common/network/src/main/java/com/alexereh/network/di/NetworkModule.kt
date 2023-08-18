package com.alexereh.network.di

import com.alexereh.network.NetworkDataSource
import com.alexereh.network.skrape.SkrapeDataSource
import com.alexereh.network.util.Endpoint
import org.koin.dsl.module

val networkModule = module {
    single<NetworkDataSource> { SkrapeDataSource(get()) }

    single { Endpoint("https://www.cs.vsu.ru/brs/login") }
}