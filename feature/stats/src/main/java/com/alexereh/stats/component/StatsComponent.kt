package com.alexereh.stats.component

import com.alexereh.model.StatisticRow
import com.alexereh.util.Result
import kotlinx.coroutines.flow.StateFlow

interface StatsComponent {
    val statRows: StateFlow<Result<List<StatisticRow>>>

    fun onItemClicked(item: StatisticRow)
    fun doProfileAction()
    fun refreshStats()
}