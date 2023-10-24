package com.alexereh.stats.component

import com.alexereh.stats.StatsStore
import kotlinx.coroutines.flow.StateFlow

interface StatsComponent {
    val state: StateFlow<StatsStore.State>
    fun doProfileAction()
    fun refreshStats()
}