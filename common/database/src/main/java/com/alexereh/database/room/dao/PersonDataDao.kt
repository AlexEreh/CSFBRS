package com.alexereh.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexereh.database.room.entities.DBPersonData

@Dao
interface PersonDataDao {
    @Query("SELECT * FROM PersonData WHERE login = :login")
    fun getPerson(
        login: String
    ): DBPersonData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: DBPersonData)
}