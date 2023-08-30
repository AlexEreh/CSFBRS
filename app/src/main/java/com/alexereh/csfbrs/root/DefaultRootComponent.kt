package com.alexereh.csfbrs.root

import com.alexereh.csfbrs.root.RootComponent.Child.LoginChild
import com.alexereh.csfbrs.root.RootComponent.Child.ProfileChild
import com.alexereh.csfbrs.root.RootComponent.Child.StatsChild
import com.alexereh.login.component.LoginComponent
import com.alexereh.login.component.RealLoginComponent
import com.alexereh.profile.component.ProfileComponent
import com.alexereh.profile.component.RealProfileComponent
import com.alexereh.stats.component.RealStatsComponent
import com.alexereh.stats.component.StatsComponent
import com.alexereh.ui.util.BaseComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, BaseComponent(componentContext),
    ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val _stack: Value<ChildStack<Config, RootComponent.Child>>
        get() = childStack(
            source = navigation,
            initialConfiguration = Config.Login,
            handleBackButton = true,
            childFactory = ::child,
        )

    override val stack: Value<ChildStack<*, RootComponent.Child>> = _stack

    private val _showSplashScreen = MutableStateFlow(true)
    override val showSplashScreen: StateFlow<Boolean>
        get() = _showSplashScreen

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child {
        return when (config) {
            is Config.Stats -> StatsChild(
                statsComponent(componentContext)
            )

            is Config.Login -> LoginChild(
                loginComponent(componentContext)
            )

            is Config.Profile -> ProfileChild(
                profileComponent(componentContext)
            )
        }
    }

    private fun statsComponent(componentContext: ComponentContext): StatsComponent {
        return RealStatsComponent(
            componentContext = componentContext,
            onItemSelected = {
            }
        ) {
            navigation.push(Config.Profile)
        }
    }

    private fun loginComponent(componentContext: ComponentContext): LoginComponent {
        return RealLoginComponent(
            componentContext = componentContext,
            onLogin = {
                navigation.replaceCurrent(Config.Stats)
            },
            onCheckStorage = {
                mainScope.launch {
                    _showSplashScreen.value = false
                }
            }
        )
    }

    private fun profileComponent(componentContext: ComponentContext): ProfileComponent {
        return RealProfileComponent(
            componentContext = componentContext,
            onBackAction = {
                navigation.pop()
            },
            onLogOut = {
                navigation.replaceAll(Config.Login)
            }
        )
    }
}