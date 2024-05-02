package com.alexereh.csfbrs

enum class BrsBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}