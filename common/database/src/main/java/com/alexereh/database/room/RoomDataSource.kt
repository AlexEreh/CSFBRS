package com.alexereh.database.room

import arrow.core.Either
import arrow.core.Option
import arrow.core.left
import arrow.core.none
import arrow.core.right
import com.alexereh.database.DatabaseDataSource
import com.alexereh.database.room.entities.DBPersonData
import com.alexereh.database.room.entities.DBStatisticRow
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow

class RoomDataSource(
    private val database: GradesDatabase
) : DatabaseDataSource {
    override suspend fun getPerson(login: String): Either<String, PersonData> {
        return try {
            return database.personDataDao.getPerson(login).asDomainModel().right()
        } catch (e: Exception) {
            e.message!!.left()
        }
    }

    override suspend fun getGrades(login: String): Either<String, List<StatisticRow>> {
        return try {
            database.statisticRowDao.getAllRowsForPerson(login).map {
                it.asDomainModel()
            }.right()
        } catch (e: Exception){
            e.message!!.left()
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