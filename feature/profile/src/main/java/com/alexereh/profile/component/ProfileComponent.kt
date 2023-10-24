package com.alexereh.profile.component

import com.alexereh.profile.ProfileStore
import kotlinx.coroutines.flow.StateFlow

interface ProfileComponent {
    val state: StateFlow<ProfileStore.State>
    fun doBackAction()
    fun doLogoutAction()
    fun refreshProfile()
}