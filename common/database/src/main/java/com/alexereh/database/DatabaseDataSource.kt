package com.alexereh.database

import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow
import com.alexereh.util.Result

interface DatabaseDataSource {
    suspend fun getPerson(
        login: String,
    ): Result<PersonData>

    suspend fun getGrades(
        login: String,
    ): Result<List<StatisticRow>>

    fun insertPerson(
        personData: PersonData,
        login: String
    )

    fun insertGrades(
        rows: List<StatisticRow>,
        login: String
    )
}