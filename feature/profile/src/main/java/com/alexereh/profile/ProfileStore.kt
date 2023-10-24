package com.alexereh.profile

import com.alexereh.model.PersonData
import com.alexereh.profile.ProfileStore.Intent
import com.alexereh.profile.ProfileStore.Label
import com.alexereh.profile.ProfileStore.State
import com.arkivanov.mvikotlin.core.store.Store

interface ProfileStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object Load : Intent
        data object Refresh : Intent
        data object Retry : Intent
    }

    sealed class State {
        data object Idle: State()
        data object Loading: State()
        data class Success(
            val data: PersonData
        ): State()
        data class Error(
            val message: String?
        ): State()
    }

    sealed interface Label
}

