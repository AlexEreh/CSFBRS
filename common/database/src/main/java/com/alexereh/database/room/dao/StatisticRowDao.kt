package com.alexereh.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexereh.database.room.entities.DBStatisticRow

@Dao
interface StatisticRowDao {
    @Query("SELECT * FROM StatisticRow WHERE user = :login")
    fun getAllRowsForPerson(
        login: String
    ): List<DBStatisticRow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRows(rows: List<DBStatisticRow>)
}