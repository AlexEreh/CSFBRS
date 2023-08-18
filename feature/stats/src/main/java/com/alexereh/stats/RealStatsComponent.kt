package com.alexereh.stats

import androidx.datastore.core.DataStore
import com.alexereh.datastore.UserData
import com.alexereh.grades.GradesRepository
import com.alexereh.model.StatisticRow
import com.alexereh.util.Result
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

fun LifecycleOwner.coroutineScope(context: CoroutineContext): CoroutineScope {
    val scope = CoroutineScope(context)
    lifecycle.doOnDestroy(scope::cancel)

    return scope
}

class RealStatsComponent(
    componentContext: ComponentContext,
    private val onItemSelected: (item: StatisticRow) -> Unit,
    private val onProfileAction: () -> Unit
) : StatsComponent, KoinComponent,
    ComponentContext by componentContext {

    // The scope is automatically cancelled when the component is destroyed
    private val scope = coroutineScope(Dispatchers.IO)

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
                    .launchIn(scope)
            }.launchIn(scope)
    }

    init {
        lifecycle.doOnCreate {
            refreshStats()
        }
    }
}