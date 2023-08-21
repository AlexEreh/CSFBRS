package com.alexereh.profile.component

import android.util.Log
import com.alexereh.datastore.UserDataSource
import com.alexereh.grades.GradesRepository
import com.alexereh.model.PersonData
import com.alexereh.ui.util.BaseComponent
import com.alexereh.util.Result
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RealProfileComponent(
    private val onBackAction: () -> Unit,
    private val onLogOut: () -> Unit,
    componentContext: ComponentContext
) : ProfileComponent, KoinComponent, BaseComponent(componentContext),
    ComponentContext by componentContext {

    private val gradesRepository: GradesRepository by inject()
    private val dataSource: UserDataSource by inject()

    private val _personData: MutableStateFlow<Result<PersonData>> = MutableStateFlow(Result.Loading)
    override val personData: StateFlow<Result<PersonData>>
        get() = _personData

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
        val data = dataSource.getLoginAndPassword()!!
        gradesRepository.getPersonData(data.login, data.password)
            .onEach {
                Log.e("Coroutine", "$it")
                _personData.value = it
            }
            .launchIn(ioScope)
    }

    init {
        lifecycle.doOnCreate {
            refreshProfile()
        }
    }

}