package com.alexereh.stats

import arrow.core.Option
import com.alexereh.datastore.UserDataSource
import com.alexereh.grades.GradesRepository
import com.alexereh.model.LoginData
import com.alexereh.model.StatisticRow
import com.alexereh.stats.StatsStore.Intent
import com.alexereh.stats.StatsStore.Label
import com.alexereh.stats.StatsStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class StatsStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): StatsStore =
        object : StatsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "StatsStore",
            initialState = State.Idle,
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object Load : Action
    }

    private sealed interface Msg {
        data class Error(
            val message: String?
        ) : Msg

        data class Success(
            val data: List<StatisticRow>
        ) : Msg

        data object LoadingStarted : Msg
    }



    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            dispatch(Action.Load)
        }
    }

    private class ExecutorImpl : KoinComponent,
        CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        val dataStore: UserDataSource by inject()
        val repo: GradesRepository by inject()

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.Load -> {
                    loadStats()
                }

                is Intent.Refresh -> {
                    loadStats()
                }

                is Intent.Retry -> {
                    loadStats()
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.Load -> {
                    dispatch(Msg.LoadingStarted)
                    loadStats()
                }
            }
        }

        private fun loadStats() {
            scope.launch {
                dispatch(Msg.LoadingStarted)
                val user: Option<LoginData> = dataStore.getLoginAndPassword()
                if (user.isNone()) {
                    dispatch(Msg.Error("Вход в пользователя не был выполнен!"))
                    return@launch
                }
                val userData = user.getOrNull()!!
                val personRows = withContext(Dispatchers.IO) {
                    repo.getPersonRows(userData.login, userData.password)
                }
                if (personRows.isNone()) {
                    dispatch(Msg.Error("Нет статистики!"))
                    return@launch
                }
                val rows = personRows.getOrNull()!!
                dispatch(Msg.Success(rows))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.Error -> State.Error(msg.message)
                is Msg.Success -> State.Success(msg.data)
                is Msg.LoadingStarted -> State.Loading
            }
    }
}
