package com.alexereh.model

enum class ScoringType {
    EXAM,
    CREDIT,
    DIFFERENTIATED_CREDIT;

    companion object {
        fun getTypeFromString(ownText: String): ScoringType {
            if (ownText == "Экзамен") return EXAM
            if (ownText == "Зачёт") return CREDIT
            if (ownText == "Зачёт с оценкой") return DIFFERENTIATED_CREDIT
            return CREDIT
        }
    }
}