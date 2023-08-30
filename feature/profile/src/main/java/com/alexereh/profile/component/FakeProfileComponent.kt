package com.alexereh.profile.component

import com.alexereh.model.PersonData
import com.alexereh.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeProfileComponent : ProfileComponent {
    override val personData: StateFlow<Resource<PersonData>>
        get() = MutableStateFlow(
            Resource.Success(PersonData.getStub())
        )

    override fun doBackAction() {
    }

    override fun doLogoutAction() {
    }

    override fun refreshProfile() {
    }
}