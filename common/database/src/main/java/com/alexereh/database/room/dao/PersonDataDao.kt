package com.alexereh.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexereh.database.room.entities.DBPersonData

@Dao
interface PersonDataDao {
    @Query("SELECT * FROM PersonData WHERE login = :login AND password = :password")
    fun getPerson(
        login: String,
        password: String
    ): DBPersonData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: DBPersonData)
}