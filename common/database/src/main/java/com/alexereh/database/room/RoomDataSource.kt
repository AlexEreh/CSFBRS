package com.alexereh.database.room

import com.alexereh.database.DatabaseDataSource
import com.alexereh.database.room.entities.DBPersonData
import com.alexereh.database.room.entities.DBStatisticRow
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow
import com.alexereh.util.Result

class RoomDataSource(
    private val database: GradesDatabase
) : DatabaseDataSource {
    override suspend fun getPerson(login: String): Result<PersonData> {
        val person = database.personDataDao.getPerson(login)
        return Result.Success(person.asDomainModel())
    }

    override suspend fun getGrades(login: String): Result<List<StatisticRow>> {
        val rows = database.statisticRowDao.getAllRowsForPerson(login)
        return Result.Success(rows.map { it.asDomainModel() })
    }

    override fun insertPerson(personData: PersonData, login: String) {
        database.personDataDao.insertPerson(
            DBPersonData.fromDomainModel(
                personData = personData,
                login = login
            )
        )
    }

    override fun insertGrades(rows: List<StatisticRow>, login: String) {
        val rowsDB = rows.map {
            DBStatisticRow.fromDomainModel(it, login)
        }
        database.statisticRowDao.insertAllRows(rowsDB)
    }

}