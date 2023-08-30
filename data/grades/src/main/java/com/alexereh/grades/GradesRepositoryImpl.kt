package com.alexereh.grades

import android.content.Context
import com.alexereh.database.DatabaseDataSource
import com.alexereh.grades.util.isNetworkAvailable
import com.alexereh.model.PersonData
import com.alexereh.model.StatisticRow
import com.alexereh.network.NetworkDataSource
import com.alexereh.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GradesRepositoryImpl(
    private val context: Context,
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource
) : GradesRepository {

    override fun getPersonData(login: String, password: String): Flow<Resource<PersonData>> {
        return flow {
            emit(Resource.Loading)
            if (!isNetworkAvailable(context)) {
                emit(databaseDataSource.getPerson(login))
            } else {
                when (val networkPerson = networkDataSource.getPerson(login, password)) {
                    is Resource.Error -> {
                        emit(networkPerson)
                    }

                    Resource.Loading -> {
                        emit(networkPerson)
                    }
                    Resource.NotLoading -> {
                        emit(networkPerson)
                    }
                    is Resource.Success -> {
                        databaseDataSource.insertPerson(
                            personData = networkPerson.data,
                            login = login
                        )
                        emit(networkPerson)
                    }
                }
            }
        }
    }

    override fun getPersonRows(login: String, password: String): Flow<Resource<List<StatisticRow>>> {
        return flow {
            emit(Resource.Loading)
            if (!isNetworkAvailable(context)) {
                emit(databaseDataSource.getGrades(login))
            } else {
                when (val networkPerson = networkDataSource.getPerson(login, password)) {
                    !is Resource.Success -> {
                        
                    }

                    else -> {
                        databaseDataSource.insertPerson(
                            personData = networkPerson.data,
                            login = login
                        )
                    }
                }
                when (val networkGrades = networkDataSource.getGrades(login, password)) {
                    is Resource.Error -> {
                        emit(networkGrades)
                    }

                    is Resource.Success -> {

                        databaseDataSource.insertGrades(
                            rows = networkGrades.data,
                            login = login
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