package com.alexereh.model

data class PersonData(
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val course: Int,
    val semester: Int,
    val group: Int,
    val subGroup: Int,
    val specialty: String,
) {
    companion object {
        fun getStub(): PersonData =
            PersonData(
                firstName = "Имя",
                lastName = "Фамилия",
                patronymic = "Отчество",
                course = 2,
                semester = 3,
                group = 7,
                subGroup = 2,
                specialty = "Программная инженерия"
            )
    }
}