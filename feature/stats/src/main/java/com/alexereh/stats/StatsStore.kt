package com.alexereh.stats

import com.alexereh.model.StatisticRow
import com.alexereh.stats.StatsStore.Intent
import com.alexereh.stats.StatsStore.Label
import com.alexereh.stats.StatsStore.State
import com.arkivanov.mvikotlin.core.store.Store

interface StatsStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object Load : Intent
        data object Refresh : Intent
        data object Retry : Intent
    }

    sealed class State {
        data object Idle: State()
        data object Loading: State()
        data class Success(
            val data: List<StatisticRow>
        ): State()
        data class Error(
            val message: String?
        ): State()
    }

    sealed interface Label {
    }
}
