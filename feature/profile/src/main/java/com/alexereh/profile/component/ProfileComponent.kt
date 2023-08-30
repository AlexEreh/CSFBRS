package com.alexereh.profile.component

import com.alexereh.model.PersonData
import com.alexereh.util.Resource
import kotlinx.coroutines.flow.StateFlow

interface ProfileComponent {
    val personData: StateFlow<Resource<PersonData>>
    fun doBackAction()
    fun doLogoutAction()
    fun refreshProfile()
}