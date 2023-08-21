package com.alexereh.login.component

import kotlinx.coroutines.flow.StateFlow

interface LoginComponent {
    fun updateLoginText(newText: String)
    fun updatePasswordText(newText: String)
    fun doLogin()
    fun signalizeError()
    val loginText: StateFlow<String>
    val passwordText: StateFlow<String>
}