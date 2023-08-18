package com.alexereh.profile

import com.alexereh.model.PersonData
import com.alexereh.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeProfileComponent : ProfileComponent {
    override val personData: StateFlow<Result<PersonData>>
        get() = MutableStateFlow(
            Result.Loading
        )

    override fun doBackAction() {
    }

    override fun doLogoutAction() {
    }

    override fun refreshProfile() {
    }
}