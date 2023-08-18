package com.alexereh.login

import androidx.datastore.core.DataStore
import com.alexereh.datastore.UserData
import com.alexereh.datastore.UserDataSource
import com.alexereh.datastore.userData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.badoo.reaktive.disposable.scope.DisposableScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal fun ComponentContext.disposableScope(): DisposableScope {
    val scope = DisposableScope()
    lifecycle.doOnDestroy(scope::dispose)
    return scope
}

class RealLoginComponent(
    componentContext: ComponentContext,
    private val onLogin: () -> Unit
) : LoginComponent, KoinComponent,
    ComponentContext by componentContext,
    DisposableScope by componentContext.disposableScope() {



    private val dataSource: UserDataSource by inject()
    private val dataStore: DataStore<UserData> by inject()

    private val _loginText = MutableStateFlow("")
    override val loginText: StateFlow<String>
        get() = _loginText

    private val _passwordText = MutableStateFlow("")
    override val passwordText: StateFlow<String>
        get() = _passwordText

    override fun updateLoginText(newText: String) {
        _loginText.value = newText
            .replaceAfter("@", "").replace("@", "")
    }

    override fun updatePasswordText(newText: String) {
        _passwordText.value = newText
    }

    override fun doLogin() {
        runBlocking(Dispatchers.IO) {
            val userData = userData {
                login = _loginText.value
                password = _passwordText.value
                loggedIn = true
            }
            dataSource.setNewUserData(userData)
        }
        onLogin()
    }

    init {
        lifecycle.doOnCreate {
            var loggedIn = false
            runBlocking(Dispatchers.IO) {
                val userdata = dataStore.data.first()
                if (userdata.loggedIn) {
                    loggedIn = true
                }
            }
            if (loggedIn) onLogin()
        }
    }
}