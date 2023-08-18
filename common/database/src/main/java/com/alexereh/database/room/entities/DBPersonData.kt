package com.alexereh.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alexereh.model.PersonData

@Entity(
    tableName = "PersonData",
    indices = [Index(value = ["login"])]
)
data class DBPersonData(
    @PrimaryKey @ColumnInfo("login") val login: String,
    @ColumnInfo("first_name") val firstName: String,
    @ColumnInfo("last_name") val lastName: String,
    @ColumnInfo("patronymic") val patronymic: String,
    @ColumnInfo("course") val course: Int,
    @ColumnInfo("semester") val semester: Int,
    @ColumnInfo("group") val group: Int,
    @ColumnInfo("subgroup") val subGroup: Int,
    @ColumnInfo("specialty") val specialty: String,
) {
    companion object {
        fun fromDomainModel(
            personData: PersonData,
            login: String,
        ): DBPersonData {
            return with(personData) {
                DBPersonData(
                    login = login,
                    firstName = firstName,
                    lastName = lastName,
                    patronymic = patronymic,
                    course = course,
                    semester = semester,
                    group = group,
                    subGroup = subGroup,
                    specialty = specialty
                )
            }
        }
    }

    fun asDomainModel(): PersonData {
        return PersonData(
            firstName = firstName,
            lastName = lastName,
            patronymic = patronymic,
            course = course,
            semester = semester,
            group = group,
            subGroup = subGroup,
            specialty = specialty,
        )
    }
}