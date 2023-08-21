package com.alexereh.ui.util

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

private fun LifecycleOwner.coroutineScope(context: CoroutineContext): CoroutineScope {
    val scope = CoroutineScope(context)
    lifecycle.doOnDestroy(scope::cancel)

    return scope
}


abstract class BaseComponent(
    componentContext: ComponentContext,
) {
    // The scope is automatically cancelled when the component is destroyed
    val ioScope = componentContext.coroutineScope(Dispatchers.IO)
    val mainScope = componentContext.coroutineScope(Dispatchers.Main.immediate)
}