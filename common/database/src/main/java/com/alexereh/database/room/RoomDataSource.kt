package com.alexereh.database.room

import arrow.core.Option
import arrow.core.none
import com.alexereh.database.DatabaseDataSource
import com.alexereh.database.room.entities.DBPersonData
import com.alexereh.database.room.entities.DBStatisticRow
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow

class RoomDataSource(
    private val database: GradesDatabase
) : DatabaseDataSource {
    override suspend fun getPerson(login: String): Option<PersonData> {
        return try {
            return Option(database.personDataDao.getPerson(login).asDomainModel())
        } catch (e: Exception) {
            none()
        }
    }

    override suspend fun getGrades(login: String): Option<List<StatisticRow>> {
        return try {
            Option.fromNullable(database.statisticRowDao.getAllRowsForPerson(login).map {
                it.asDomainModel()
            })
        } catch (e: Exception){
            none()
        }
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