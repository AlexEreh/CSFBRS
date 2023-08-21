package com.alexereh.stats.component

import androidx.datastore.core.DataStore
import com.alexereh.datastore.UserData
import com.alexereh.grades.GradesRepository
import com.alexereh.model.StatisticRow
import com.alexereh.ui.util.BaseComponent
import com.alexereh.util.Result
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RealStatsComponent(
    componentContext: ComponentContext,
    private val onItemSelected: (item: StatisticRow) -> Unit,
    private val onProfileAction: () -> Unit
) : StatsComponent, KoinComponent, BaseComponent(componentContext),
    ComponentContext by componentContext {

    private val dataStore: DataStore<UserData> by inject()
    private val repo: GradesRepository by inject()

    private val _statRows: MutableStateFlow<Result<List<StatisticRow>>> =
        MutableStateFlow(Result.NotLoading)
    override val statRows: StateFlow<Result<List<StatisticRow>>>
        get() = _statRows

    override fun onItemClicked(item: StatisticRow) {
        onItemSelected(item)
    }

    override fun doProfileAction() {
        onProfileAction()
    }

    override fun refreshStats() {
        dataStore.data
            .onEach { user ->
                repo.getPersonRows(user.login, user.password)
                    .onEach {
                        _statRows.value = it
                    }
                    .launchIn(ioScope)
            }.launchIn(ioScope)
    }

    init {
        lifecycle.doOnCreate {
            refreshStats()
        }
    }
}