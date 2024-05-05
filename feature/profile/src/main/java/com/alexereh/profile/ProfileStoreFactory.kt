package com.alexereh.profile

import com.alexereh.datastore.UserDataSource
import com.alexereh.grades.GradesRepository
import com.alexereh.model.PersonData
import com.alexereh.profile.ProfileStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ProfileStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): ProfileStore =
        object : ProfileStore,
            Store<ProfileStore.Intent, State, ProfileStore.Label> by storeFactory.create(
                name = "ProfileStore",
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
            val data: PersonData
        ): Msg
        data object LoadingStarted : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            dispatch(Action.Load)
        }
    }

    private class ExecutorImpl : KoinComponent,
        CoroutineExecutor<ProfileStore.Intent, Action, State, Msg, ProfileStore.Label>() {

        val dataSource: UserDataSource by inject()
        val repo: GradesRepository by inject()

        override fun executeIntent(intent: ProfileStore.Intent, getState: () -> State) {
            when (intent) {
                is ProfileStore.Intent.Load -> loadProfile()
                is ProfileStore.Intent.Refresh -> loadProfile()
                is ProfileStore.Intent.Retry -> loadProfile()
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.Load -> {
                    dispatch(Msg.LoadingStarted)
                    loadProfile()
                }
            }
        }

        private fun loadProfile(){
            scope.launch {
                val data = dataSource.getLoginAndPassword()
                if (data.isNone()) {
                    dispatch(Msg.Error("Вход в пользователя не был выполнен!"))
                    return@launch
                }
                val dataReal = data.getOrNull()!!
                val personData = repo.getPersonData(dataReal.login, dataReal.password)
                if (personData.isLeft()) {
                    dispatch(Msg.Error("Не удалось получить пользователя!\nСообщение: ${personData.leftOrNull()!!}"))
                    return@launch
                }
                dispatch(Msg.Success(personData.getOrNull()!!))
            }

        }

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.Error -> State.Error(msg.message)
                is Msg.LoadingStarted -> State.Loading
                is Msg.Success -> State.Success(msg.data)
            }
    }
}