package com.alexereh.login.component

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import com.alexereh.datastore.UserData
import com.alexereh.datastore.UserDataSource
import com.alexereh.datastore.userData
import com.alexereh.grades.GradesRepository
import com.alexereh.ui.util.BaseComponent
import com.alexereh.util.Resource
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNot
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

    private val dataSource: UserDataSource by inject()
    private val context: Context by inject()
    private val dataStore: DataStore<UserData> by inject()
    private val gradesRepository: GradesRepository by inject()

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
        ioScope.launch {
            val person = gradesRepository.getPersonData(_loginText.value, _passwordText.value)
                .filterNot { it is Resource.Loading || it is Resource.NotLoading }
                .first()
            if (person is Resource.Error) {
                signalizeError()
            } else if (person is Resource.Success) {
                val userData = userData {
                    login = _loginText.value
                    password = _passwordText.value
                    loggedIn = true
                }
                dataSource.setNewUserData(userData)
                mainScope.launch {
                    onLogin()
                }
            }
        }
    }

    override fun signalizeError() {
        mainScope.launch {
            Toast.makeText(context, "Unable to login", Toast.LENGTH_SHORT).show()
        }
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
    }
}