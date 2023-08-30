package com.alexereh.database.room

import com.alexereh.database.DatabaseDataSource
import com.alexereh.database.room.entities.DBPersonData
import com.alexereh.database.room.entities.DBStatisticRow
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow
import com.alexereh.util.Resource

class RoomDataSource(
    private val database: GradesDatabase
) : DatabaseDataSource {
    override suspend fun getPerson(login: String): Resource<PersonData> {
        return try {
            val person = database.personDataDao.getPerson(login)
            Resource.Success(person.asDomainModel())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getGrades(login: String): Resource<List<StatisticRow>> {
        return try {
            val rows = database.statisticRowDao.getAllRowsForPerson(login)
            Resource.Success(rows.map { it.asDomainModel() })
        } catch (e: Exception){
            Resource.Error(e)
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