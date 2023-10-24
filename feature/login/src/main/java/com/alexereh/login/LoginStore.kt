package com.alexereh.login

import com.alexereh.login.LoginStore.Intent
import com.alexereh.login.LoginStore.Label
import com.alexereh.login.LoginStore.State
import com.arkivanov.mvikotlin.core.store.Store

interface LoginStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeLogin(val newLogin: String): Intent
        data class ChangePassword(val newPassword: String): Intent
        data object MakeLogin: Intent
    }

    data class State(
        val loginText: String = "",
        val passwordText: String = "",
        val hasLogin: Boolean = false,
        val onLogin: () -> Unit = {},
        val onCheckStorage: (Boolean) -> Unit = {}
    )

    sealed interface Label
}

