@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("dev.shreyaspatil.compose-compiler-report-generator") version "1.1.0"
}

android {
    namespace = "com.alexereh.login"
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
        kotlinCompilerExtensionVersion = "1.5.7"
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
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)


    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.protobuf.kotlin.lite)

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation(project(mapOf("path" to ":common:ui")))

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(project(mapOf("path" to ":common:model")))
    implementation(project(mapOf("path" to ":data:grades")))
    implementation(project(mapOf("path" to ":common:datastore")))
    implementation(project(":common:util"))
    implementation(project(":common:util"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}