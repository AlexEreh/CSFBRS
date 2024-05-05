package com.alexereh.database

import arrow.core.Either
import arrow.core.Option
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow

interface DatabaseDataSource {
    suspend fun getPerson(
        login: String,
    ): Either<String, PersonData>

    suspend fun getGrades(
        login: String,
    ): Either<String, List<StatisticRow>>

    fun insertPerson(
        personData: PersonData,
        login: String
    )

    fun insertGrades(
        rows: List<StatisticRow>,
        login: String
    )
}