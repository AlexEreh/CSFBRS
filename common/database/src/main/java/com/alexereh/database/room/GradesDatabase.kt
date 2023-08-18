package com.alexereh.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexereh.database.room.dao.PersonDataDao
import com.alexereh.database.room.dao.StatisticRowDao
import com.alexereh.database.room.entities.DBPersonData
import com.alexereh.database.room.entities.DBStatisticRow

@Database(entities = [DBPersonData::class, DBStatisticRow::class], version = 1)
abstract class GradesDatabase : RoomDatabase() {
    abstract val personDataDao: PersonDataDao
    abstract val statisticRowDao: StatisticRowDao
}