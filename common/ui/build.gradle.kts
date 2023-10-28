@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("dev.shreyaspatil.compose-compiler-report-generator") version "1.1.0"
}

android {
    namespace = "com.alexereh.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(libs.decompose.jetpack.compose)
    api(libs.decompose.main)

    api("com.arkivanov.mvikotlin:mvikotlin:3.2.1")
    api("com.arkivanov.mvikotlin:mvikotlin-main:3.2.1")
    api("com.arkivanov.mvikotlin:mvikotlin-logging:3.2.1")
    api("com.arkivanov.mvikotlin:mvikotlin-timetravel:3.2.1")
    api("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:3.2.1")

    api("io.arrow-kt:arrow-core:1.2.1")
    api("io.arrow-kt:arrow-fx-coroutines:1.2.1")

    api(platform(libs.compose.bom))
    api(libs.material3)
    api(libs.material3.window.size)
    api("androidx.compose.material:material-icons-extended")
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.activity.compose)
    api(libs.androidx.ui.util)
    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.tooling.preview)

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}