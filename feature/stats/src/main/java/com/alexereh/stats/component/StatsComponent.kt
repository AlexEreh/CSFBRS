package com.alexereh.stats.component

import com.alexereh.model.StatisticRow
import com.alexereh.util.Resource
import kotlinx.coroutines.flow.StateFlow

interface StatsComponent {
    val statRows: StateFlow<Resource<List<StatisticRow>>>

    fun onItemClicked(item: StatisticRow)
    fun doProfileAction()
    fun refreshStats()
}