package com.alexereh.csfbrs.root

import com.alexereh.login.LoginComponent
import com.alexereh.profile.ProfileComponent
import com.alexereh.stats.StatsComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    //val currentBottomNavItem: MutableStateFlow<BottomNavItem>
    sealed class Child {
        class StatsChild(val component: StatsComponent) : Child()
        class LoginChild(val component: LoginComponent) : Child()
        class ProfileChild(val component: ProfileComponent) : Child()
    }
}