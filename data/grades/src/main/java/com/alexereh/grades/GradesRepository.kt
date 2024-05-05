package com.alexereh.grades

import arrow.core.Either
import arrow.core.Option
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow

interface GradesRepository {
    suspend fun getPersonData(login: String, password: String): Either<String, PersonData>
    suspend fun getPersonRows(login: String, password: String): Either<String, List<StatisticRow>>
}
