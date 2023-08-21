package com.alexereh.profile.component

import com.alexereh.model.PersonData
import com.alexereh.util.Result
import kotlinx.coroutines.flow.StateFlow

interface ProfileComponent {
    val personData: StateFlow<Result<PersonData>>
    fun doBackAction()
    fun doLogoutAction()
    fun refreshProfile()
}