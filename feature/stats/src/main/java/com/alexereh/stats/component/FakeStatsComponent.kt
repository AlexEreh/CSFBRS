package com.alexereh.stats.component

import com.alexereh.model.StatisticRow
import com.alexereh.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeStatsComponent(
) : StatsComponent {
    override val statRows: StateFlow<Result<List<StatisticRow>>> =
        MutableStateFlow(Result.Success(MutableList(50) { StatisticRow.getStub() }))

    override fun onItemClicked(item: StatisticRow) {
    }

    override fun doProfileAction() {
    }

    override fun refreshStats() {
    }
}