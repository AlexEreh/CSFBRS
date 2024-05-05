package com.alexereh.grades

import android.content.Context
import arrow.core.Either
import arrow.core.Option
import com.alexereh.database.DatabaseDataSource
import com.alexereh.grades.util.isNetworkAvailable
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow
import com.alexereh.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GradesRepositoryImpl(
    private val context: Context,
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource
) : GradesRepository {

    override suspend fun getPersonData(login: String, password: String): Either<String, PersonData> {
        if (!isNetworkAvailable(context)) {
            return withContext(Dispatchers.IO) {
                databaseDataSource.getPerson(login)
            }
        }
        val networkPerson = withContext(Dispatchers.IO) {
            networkDataSource.getPerson(login, password)
        }
        if (networkPerson.isRight()) {
            withContext(Dispatchers.IO){
                databaseDataSource.insertPerson(
                    personData = networkPerson.getOrNull()!!,
                    login = login
                )
            }
        }
        return withContext(Dispatchers.IO) {
            databaseDataSource.getPerson(login)
        }
    }

    override suspend fun getPersonRows(login: String, password: String): Either<String, List<StatisticRow>> {
        if (!isNetworkAvailable(context)) {
            val dbGrades = withContext(Dispatchers.IO) {
                databaseDataSource.getGrades(login)
            }
            return dbGrades
        }
        val networkPerson = withContext(Dispatchers.IO) {
            networkDataSource.getPerson(login, password)
        }
        if (networkPerson.isRight()) {
            databaseDataSource.insertPerson(networkPerson.getOrNull()!!, login)
        }
        return withContext(Dispatchers.IO) {
            networkDataSource.getGrades(login, password)
        }
    }
}