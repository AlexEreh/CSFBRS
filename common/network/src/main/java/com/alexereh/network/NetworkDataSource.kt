package com.alexereh.network

import arrow.core.Either
import arrow.core.Option
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow

interface NetworkDataSource {
    suspend fun getGrades(
        login: String,
        password: String
    ): Either<String, List<StatisticRow>>

    suspend fun getPerson(
        login: String,
        password: String
    ): Either<String, PersonData>
}