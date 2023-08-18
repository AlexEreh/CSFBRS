package com.alexereh.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.alexereh.model.Marks
import com.alexereh.model.ScoringType
import com.alexereh.model.StatisticRow

@Entity(
    tableName = "StatisticRow",
    foreignKeys = [
        ForeignKey(
            entity = DBPersonData::class,
            parentColumns = arrayOf("login"),
            childColumns = arrayOf("user")
        )
    ],
    primaryKeys = [
        "user", "course", "semester", "discipline"
    ]
)
data class DBStatisticRow(
    @ColumnInfo("user") val user: String,
    @ColumnInfo("study_year") val studyYear: String,
    @ColumnInfo("semester") val semesterNumber: Byte,
    @ColumnInfo("course") val courseNumber: Byte,
    @ColumnInfo("discipline") val disciplineName: String,
    @ColumnInfo("scoring") val scoringType: ScoringType,
    @ColumnInfo("tutor") val tutor: String,
    @ColumnInfo("first_score") val firstAttestationScore: Int? = null,
    @ColumnInfo("second_score") val secondAttestationScore: Int? = null,
    @ColumnInfo("third_score") val thirdAttestationScore: Int? = null,
    @ColumnInfo("exam_score") val examScore: Int? = null,
    @ColumnInfo("additional_score") val additionalScore: Int? = null,
    @ColumnInfo("result_score") val resultScore: Int? = null,
    @ColumnInfo("result_string") val resultString: String? = null
) {
    companion object {
        fun fromDomainModel(
            row: StatisticRow,
            login: String
        ): DBStatisticRow {
            return with(row) {
                DBStatisticRow(
                    user = login,
                    studyYear = studyYear,
                    semesterNumber = semesterNumber,
                    courseNumber = courseNumber,
                    disciplineName = disciplineName,
                    scoringType = scoringType,
                    tutor = tutor,
                    firstAttestationScore = marks.firstAttestationScore,
                    secondAttestationScore = marks.secondAttestationScore,
                    thirdAttestationScore = marks.thirdAttestationScore,
                    examScore = marks.examScore,
                    additionalScore = marks.additionalScore,
                    resultScore = marks.resultScore,
                    resultString = marks.resultString
                )
            }
        }
    }

    fun asDomainModel(): StatisticRow {
        return StatisticRow(
            studyYear = studyYear,
            semesterNumber = semesterNumber,
            courseNumber = courseNumber,
            disciplineName = disciplineName,
            scoringType = scoringType,
            tutor = tutor,
            marks = Marks(
                firstAttestationScore,
                secondAttestationScore,
                thirdAttestationScore,
                examScore,
                additionalScore,
                resultScore,
                resultString
            )
        )
    }
}