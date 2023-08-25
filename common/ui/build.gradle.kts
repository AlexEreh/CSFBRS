@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
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
        kotlinCompilerExtensionVersion = "1.5.1"
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

    api(libs.reaktive.reaktive)
    api(libs.reaktive.annotations)
    api(libs.reaktive.reaktiveTesting)
    api(libs.reaktive.coroutinesInterop)

    api("com.google.accompanist:accompanist-systemuicontroller:0.33.1-alpha")

    api(platform(libs.compose.bom))
    api(libs.material3)
    api(libs.material3.window.size)
    api("androidx.compose.material:material-icons-extended")
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.activity.compose)
    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.tooling.preview)

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}