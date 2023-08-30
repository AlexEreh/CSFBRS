package com.alexereh.database

import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow
import com.alexereh.util.Resource

interface DatabaseDataSource {
    suspend fun getPerson(
        login: String,
    ): Resource<PersonData>

    suspend fun getGrades(
        login: String,
    ): Resource<List<StatisticRow>>

    fun insertPerson(
        personData: PersonData,
        login: String
    )

    fun insertGrades(
        rows: List<StatisticRow>,
        login: String
    )
}