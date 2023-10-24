package com.alexereh.profile.component

import com.alexereh.datastore.UserDataSource
import com.alexereh.profile.ProfileStore
import com.alexereh.profile.ProfileStoreFactory
import com.alexereh.ui.util.BaseComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RealProfileComponent(
    private val onBackAction: () -> Unit,
    private val onLogOut: () -> Unit,
    componentContext: ComponentContext
) : ProfileComponent, KoinComponent, BaseComponent(componentContext),
    ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        ProfileStoreFactory(
            storeFactory = DefaultStoreFactory()
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = store.stateFlow

    private val dataSource: UserDataSource by inject()

    override fun doBackAction() {
        onBackAction()
    }

    override fun doLogoutAction() {
        ioScope.launch {
            dataSource.setLoggedIn(false)
            mainScope.launch {
                onLogOut()
            }
        }
    }

    override fun refreshProfile() {
        store.accept(ProfileStore.Intent.Refresh)
    }

    init {
        lifecycle.doOnCreate(::refreshProfile)
        lifecycle.doOnDestroy(store::dispose)
    }

}