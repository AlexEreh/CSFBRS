package com.alexereh.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FakeLoginComponent() : LoginComponent {

    private val _loginText = MutableStateFlow("ereshkin_a_v")
    override val loginText: StateFlow<String>
        get() = _loginText

    private val _passwordText = MutableStateFlow("bla-bla-bla")
    override val passwordText: StateFlow<String>
        get() = _passwordText

    override fun updateLoginText(newText: String) {
        _loginText.update { it }
    }

    override fun updatePasswordText(newText: String) {
        _passwordText.update { it }
    }

    override fun doLogin() {
    }
}