package com.alexereh.login

import kotlinx.coroutines.flow.StateFlow

interface LoginComponent {
    fun updateLoginText(newText: String)
    fun updatePasswordText(newText: String)
    fun doLogin()
    val loginText: StateFlow<String>
    val passwordText: StateFlow<String>
}