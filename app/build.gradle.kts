plugins {
    // Android application and Kotlin plugins
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Jetpack Compose Compiler Plugin
    alias(libs.plugins.compose.compiler)
    // Dagger Hilt
    id("com.google.dagger.hilt.android")
    // Kotlin Symbol Processing (KSP) Plugin
    id("com.google.devtools.ksp")
}

android {
    namespace = "dev.xget.freetogame"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.xget.freetogame"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("17"))
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core AndroidX libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Navigation components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.navigation.compose)

    // Lifecycle components
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.extensions)

    // Networking (Retrofit, Gson, Logging)
    implementation(libs.retrofit) // Retrofit for API calls
    implementation(libs.converter.gson) // Gson converter for Retrofit
    implementation(libs.logging.interceptor) // Logging for HTTP requests

    // JSON Parsing
    implementation(libs.gson)

    // Image Loading (Coil)
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // Room (Database)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Testing
    testImplementation (libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit) // Unit testing
    androidTestImplementation(libs.androidx.junit) // Instrumentation tests
    androidTestImplementation(libs.androidx.espresso.core) // Espresso UI testing
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Compose test BOM
    androidTestImplementation(libs.androidx.ui.test.junit4) // UI testing with Compose
    debugImplementation(libs.androidx.ui.test.manifest) // Debug manifest testing
    runtimeOnly(libs.androidx.ui.tooling) // UI tooling runtime for Compose

    // Compose Material3 (duplicated in some cases)
    implementation(libs.androidx.material3)

    // Dagger Hilt dependencies
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // For AndroidX components, if you haven't already
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.hilt.work)

    //
}

