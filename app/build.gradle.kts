plugins {
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.alexereh.csfbrs"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alexereh.csfbrs"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        //jvmToolchain()
    }
    kotlin {
        jvmToolchain(8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE*"
            excludes += "META-INF/DEPENDENCIES*"
            excludes += "mozilla/public-suffix-list.txt"
        }
    }
    buildToolsVersion = "34.0.0"
    ndkVersion = "25.1.8937393"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.coil.kt)

    implementation(libs.androidx.core.splashscreen)

    implementation(project(mapOf("path" to ":feature:profile")))
    implementation(project(mapOf("path" to ":feature:stats")))
    implementation(project(mapOf("path" to ":feature:login")))
    implementation(project(mapOf("path" to ":data:grades")))
    implementation(project(mapOf("path" to ":common:ui")))
    implementation(project(mapOf("path" to ":common:model")))
    implementation(project(mapOf("path" to ":common:datastore")))

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore)

    implementation(libs.protobuf.kotlin.lite)

    /// Room
    annotationProcessor(libs.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.room.compiler)
}