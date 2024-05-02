import com.alexereh.csfbrs.BrsBuildType

plugins {
    alias(libs.plugins.com.google.devtools.ksp)
    id("csfbrs.android.application")
    id("csfbrs.android.application.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.alexereh.csfbrs"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alexereh.csfbrs"
        minSdk = 25
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            multiDexEnabled = true
            applicationIdSuffix = BrsBuildType.RELEASE.applicationIdSuffix
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = BrsBuildType.DEBUG.applicationIdSuffix
        }
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

    implementation(project(":feature:profile"))
    implementation(project(":feature:stats"))
    implementation(project(":feature:login"))
    implementation(project(":data:grades"))
    implementation(project(":common:ui"))
    implementation(project(":common:model"))
    implementation(project(":common:datastore"))

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.bundles.datastore)

    implementation(libs.protobuf.kotlin.lite)

    /// Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.room.compiler)
}