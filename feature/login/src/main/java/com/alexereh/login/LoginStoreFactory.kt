package com.alexereh.login

import com.alexereh.datastore.UserDataSource
import com.alexereh.datastore.userData
import com.alexereh.grades.GradesRepository
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

internal class LoginStoreFactory(
    private val storeFactory: StoreFactory,
    private val onLogin: () -> Unit,
    private val onCheckStorage: (Boolean) -> Unit
) {

    fun create(): LoginStore =
        object : LoginStore, Store<LoginStore.Intent, LoginStore.State, LoginStore.Label> by storeFactory.create(
            name = "LoginStore",
            initialState = LoginStore.State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(onLogin) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeLogin(val newLogin: String): Msg
        data class ChangePassword(val newPassword: String): Msg
        data object StartLogin: Msg
        data object LoginSuccess: Msg
    }

    private class BootstrapperImpl(
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private class ExecutorImpl(
        private val onLogin: () -> Unit
    ) : CoroutineExecutor<LoginStore.Intent, Action, LoginStore.State, Msg, LoginStore.Label>(),
        KoinComponent {

        private val dataSource: UserDataSource by inject()
        private val repo: GradesRepository by inject()

        override fun executeIntent(intent: LoginStore.Intent, getState: () -> LoginStore.State) {
            when (intent) {
                is LoginStore.Intent.ChangeLogin -> {
                    val newLogin = intent.newLogin
                        .replaceAfter("@", "")
                        .replace("@", "")
                    dispatch(Msg.ChangeLogin(newLogin))
                }
                is LoginStore.Intent.ChangePassword -> {
                    dispatch(Msg.ChangePassword(intent.newPassword))
                }
                is LoginStore.Intent.MakeLogin -> {
                    makeLogin(getState)
                }
            }
        }

        private fun makeLogin(getState: () -> LoginStore.State) {
            scope.launch {
                dispatch(Msg.StartLogin)
                val state = getState()
                val person = repo.getPersonData(state.loginText, state.passwordText)
                if (person.isLeft()) {
                    return@launch
                }
                val userData = userData {
                    login = state.loginText
                    password = state.passwordText
                    loggedIn = true
                }
                withContext(Dispatchers.IO) { dataSource.setNewUserData(userData) }
                withContext(Dispatchers.Main) {
                    onLogin()
                }
                dispatch(Msg.LoginSuccess)
            }
        }

        override fun executeAction(action: Action, getState: () -> LoginStore.State) {
        }
    }

    private object ReducerImpl : Reducer<LoginStore.State, Msg> {
        override fun LoginStore.State.reduce(msg: Msg): LoginStore.State =
            when (msg) {
                is Msg.ChangeLogin -> this.copy(loginText = msg.newLogin)
                is Msg.ChangePassword -> this.copy(passwordText = msg.newPassword)
                Msg.LoginSuccess -> this.copy(hasLogin = true)
                Msg.StartLogin -> this
            }
    }
}