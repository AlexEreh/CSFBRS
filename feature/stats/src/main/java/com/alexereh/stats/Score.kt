package com.alexereh.stats

import androidx.compose.runtime.Immutable

@Immutable
data class Score(
    val first: Int?,
    val second: Int?,
    val third: Int?,
    val exam: Int?,
    val overall: Int?,
)

