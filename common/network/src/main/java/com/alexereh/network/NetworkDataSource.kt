package com.alexereh.network

import arrow.core.Option
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow

interface NetworkDataSource {
    suspend fun getGrades(
        login: String,
        password: String
    ): Option<List<StatisticRow>>

    suspend fun getPerson(
        login: String,
        password: String
    ): Option<PersonData>
}