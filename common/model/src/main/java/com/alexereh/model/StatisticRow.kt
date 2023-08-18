package com.alexereh.model

data class StatisticRow(
    val studyYear: String,
    val semesterNumber: Byte,
    val courseNumber: Byte,
    val disciplineName: String,
    val scoringType: ScoringType,
    val tutor: String,
    val marks: Marks
) {
    companion object {
        fun getStub(): StatisticRow {
            return StatisticRow(
                studyYear = "2022-2023",
                semesterNumber = 1,
                courseNumber = 1,
                disciplineName = "История",
                scoringType = ScoringType.EXAM,
                tutor = "Фамилия И. О.",
                marks = Marks.getStub()
            )
        }
    }
}