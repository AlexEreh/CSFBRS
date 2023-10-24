package com.alexereh.stats.component

import com.alexereh.stats.StatsStore
import com.alexereh.stats.StatsStoreFactory
import com.alexereh.ui.util.BaseComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

class RealStatsComponent(
    private val onProfileAction: () -> Unit,
    componentContext: ComponentContext
) : StatsComponent, KoinComponent, BaseComponent(componentContext),
    ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        StatsStoreFactory(
            storeFactory = DefaultStoreFactory()
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<StatsStore.State> = store.stateFlow

    override fun doProfileAction() {
        onProfileAction()
    }

    override fun refreshStats() {
        store.accept(StatsStore.Intent.Refresh)
    }

    init {
        lifecycle.doOnCreate(::refreshStats)
        lifecycle.doOnDestroy(store::dispose)
    }
}