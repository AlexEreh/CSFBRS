package com.alexereh.network

import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow
import com.alexereh.util.Result

interface NetworkDataSource {
    suspend fun getGrades(
        login: String,
        password: String
    ): Result<List<StatisticRow>>

    suspend fun getPerson(
        login: String,
        password: String
    ): Result<PersonData>
}