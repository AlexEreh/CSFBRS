@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("csfbrs.android.library")
    id("csfbrs.android.library.compose")
    id("csfbrs.android.feature")
}

android {
    namespace = "com.alexereh.stats"
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
}

dependencies {
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(project(":data:grades"))
    implementation(project(":common:ui"))
    implementation(project(":common:model"))
    implementation(project(":common:datastore"))
    implementation(project(":common:util"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}