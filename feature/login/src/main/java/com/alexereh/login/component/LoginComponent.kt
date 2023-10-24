package com.alexereh.login.component

import com.alexereh.login.LoginStore
import kotlinx.coroutines.flow.StateFlow

interface LoginComponent {
    fun updateLoginText(newText: String)
    fun updatePasswordText(newText: String)
    fun doLogin()
    val state: StateFlow<LoginStore.State>
}