package com.alexereh.login.component

import androidx.datastore.core.DataStore
import com.alexereh.datastore.UserData
import com.alexereh.login.LoginStore
import com.alexereh.login.LoginStoreFactory
import com.alexereh.ui.util.BaseComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RealLoginComponent(
    componentContext: ComponentContext,
    private val onLogin: () -> Unit,
    private val onCheckStorage: (Boolean) -> Unit
) : LoginComponent, KoinComponent, BaseComponent(componentContext),
    ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        LoginStoreFactory(
            storeFactory = DefaultStoreFactory(),
            onLogin = onLogin,
            onCheckStorage = onCheckStorage
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = store.stateFlow

    private val dataStore: DataStore<UserData> by inject()

    override fun updateLoginText(newText: String) {
        store.accept(LoginStore.Intent.ChangeLogin(newText))
    }

    override fun updatePasswordText(newText: String) {
        store.accept(LoginStore.Intent.ChangePassword(newText))
    }

    override fun doLogin() {
        store.accept(LoginStore.Intent.MakeLogin)
    }

    init {
        lifecycle.doOnCreate {
            ioScope.launch {
                val userData = dataStore.data.first()

                if (userData.loggedIn) {
                    mainScope.launch {
                        onLogin()
                    }
                    ioScope.launch {
                        //delay(1_500L)
                        onCheckStorage(!userData.loggedIn)
                    }
                } else {
                    onCheckStorage(!userData.loggedIn)
                }
            }
        }
        lifecycle.doOnDestroy(store::dispose)
    }
}