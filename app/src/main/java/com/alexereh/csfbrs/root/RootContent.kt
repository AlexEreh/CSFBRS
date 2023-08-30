package com.alexereh.csfbrs.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexereh.login.ui.LoginContent
import com.alexereh.profile.ui.ProfileContent
import com.alexereh.stats.ui.StatsContent
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) { createdChild ->
        when (val child = createdChild.instance) {
            is RootComponent.Child.StatsChild -> StatsContent(
                component = child.component
            )

            is RootComponent.Child.LoginChild -> LoginContent(
                component = child.component
            )

            is RootComponent.Child.ProfileChild -> ProfileContent(
                component = child.component
            )
        }
    }
}