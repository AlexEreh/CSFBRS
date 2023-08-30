package com.alexereh.csfbrs.root

import com.alexereh.login.component.LoginComponent
import com.alexereh.profile.component.ProfileComponent
import com.alexereh.stats.component.StatsComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    val showSplashScreen: StateFlow<Boolean>

    sealed class Child {
        class StatsChild(val component: StatsComponent) : Child()
        class LoginChild(val component: LoginComponent) : Child()
        class ProfileChild(val component: ProfileComponent) : Child()
    }
}