package com.alexereh.profile

import android.util.Log
import com.alexereh.datastore.UserDataSource
import com.alexereh.grades.GradesRepository
import com.alexereh.model.PersonData
import com.alexereh.util.Result
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

fun LifecycleOwner.coroutineScope(context: CoroutineContext): CoroutineScope {
    val scope = CoroutineScope(context)
    lifecycle.doOnDestroy(scope::cancel)

    return scope
}

class RealProfileComponent(
    private val onBackAction: () -> Unit,
    private val onLogOut: () -> Unit,
    componentContext: ComponentContext
) : ProfileComponent, KoinComponent,
    ComponentContext by componentContext {

    // The scope is automatically cancelled when the component is destroyed
    private val scope = coroutineScope(Dispatchers.IO)

    private val gradesRepository: GradesRepository by inject()
    private val dataSource: UserDataSource by inject()

    private val _personData: MutableStateFlow<Result<PersonData>> = MutableStateFlow(Result.Loading)
    override val personData: StateFlow<Result<PersonData>>
        get() = _personData

    override fun doBackAction() {
        onBackAction()
    }

    override fun doLogoutAction() {
        runBlocking(Dispatchers.IO) {
            dataSource.setLoggedIn(false)
        }
        onLogOut()
    }

    override fun refreshProfile() {
        val data = dataSource.getLoginAndPassword()!!
        gradesRepository.getPersonData(data.login, data.password)
            .onEach {
                Log.e("Coroutine", "$it")
                _personData.value = it
            }
            .launchIn(scope)
    }

    init {
        lifecycle.doOnCreate {
            refreshProfile()
        }
    }

}