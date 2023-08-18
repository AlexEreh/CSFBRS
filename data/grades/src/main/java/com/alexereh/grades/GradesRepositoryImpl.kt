package com.alexereh.grades

import android.content.Context
import com.alexereh.database.DatabaseDataSource
import com.alexereh.grades.util.isNetworkAvailable
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow
import com.alexereh.network.NetworkDataSource
import com.alexereh.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GradesRepositoryImpl(
    private val context: Context,
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource
) : GradesRepository {

    override fun getPersonData(login: String, password: String): Flow<Result<PersonData>> {
        return flow {
            emit(Result.Loading)
            if (!isNetworkAvailable(context)) {
                emit(databaseDataSource.getPerson(login, password))
            } else {
                when (val networkPerson = networkDataSource.getPerson(login, password)) {
                    is Result.Error -> {
                        emit(networkPerson)
                    }

                    Result.Loading -> {
                        emit(networkPerson)
                    }
                    Result.NotLoading -> {
                        emit(networkPerson)
                    }
                    is Result.Success -> {
                        databaseDataSource.insertPerson(
                            personData = networkPerson.data,
                            login = login,
                            password = password
                        )
                        emit(networkPerson)
                    }
                }
            }
        }
    }

    override fun getPersonRows(login: String, password: String): Flow<Result<List<StatisticRow>>> {
        return flow {
            emit(Result.Loading)
            if (!isNetworkAvailable(context)) {
                emit(databaseDataSource.getGrades(login, password))
            } else {
                when (val networkPerson = networkDataSource.getPerson(login, password)) {
                    !is Result.Success -> {

                    }

                    else -> {
                        databaseDataSource.insertPerson(
                            personData = networkPerson.data,
                            login = login,
                            password = password
                        )
                    }
                }
                when (val networkGrades = networkDataSource.getGrades(login, password)) {
                    is Result.Error -> {
                        emit(networkGrades)
                    }

                    is Result.Success -> {

                        databaseDataSource.insertGrades(
                            rows = networkGrades.data,
                            login = login,
                            password = password
                        )
                        emit(networkGrades)
                    }

                    else -> {
                    }
                }
            }
        }
    }
}