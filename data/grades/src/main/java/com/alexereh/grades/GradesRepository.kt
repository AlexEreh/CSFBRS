package com.alexereh.grades

import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow
import com.alexereh.util.Result
import kotlinx.coroutines.flow.Flow

interface GradesRepository {
    fun getPersonData(login: String, password: String): Flow<Result<PersonData>>
    fun getPersonRows(login: String, password: String): Flow<Result<List<StatisticRow>>>
}
