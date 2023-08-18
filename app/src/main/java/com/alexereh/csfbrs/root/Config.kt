package com.alexereh.csfbrs.root

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface Config : Parcelable {
    @Parcelize
    data object Stats : Config

    @Parcelize
    data object Login : Config

    @Parcelize
    data object Profile : Config
}