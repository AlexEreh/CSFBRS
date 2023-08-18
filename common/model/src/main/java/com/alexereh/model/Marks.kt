package com.alexereh.model

data class Marks (
    val firstAttestationScore: Int? = null,
    val secondAttestationScore: Int? = null,
    val thirdAttestationScore: Int? = null,
    val examScore: Int? = null,
    val additionalScore: Int? = null,
    val resultScore: Int? = null,
    val resultString: String? = null
) {
    companion object {
        fun getStub(): Marks {
            return Marks(
                firstAttestationScore = 27,
                secondAttestationScore = 44,
                thirdAttestationScore = 44,
                examScore = 49,
                additionalScore = 0,
                resultScore = 91,
                resultString = "отлично"
            )
        }
    }
}