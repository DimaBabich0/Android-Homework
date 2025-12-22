plugins {
    alias(libs.plugins.android.application)
    kotlin("android") version "2.1.20" // !!! https://kotlinlang.org/docs/whatsnew2120.html
    kotlin("kapt") version "2.1.20" // !!! плагін Gradle, який потрібен, щоб обробляти анотації в Kotlin-коді
}

android {
    namespace = "com.dima.app1"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.dima.app1"
        minSdk = 31
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"  // !!!
    }

    buildFeatures {
        viewBinding = true // !!!
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.activity:activity:1.12.2")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1") // https://developer.android.com/jetpack/androidx/releases/constraintlayout
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0") // https://developer.android.com/jetpack/androidx/releases/recyclerview
    implementation("com.google.android.material:material:1.13.0")

    // Room
    val roomVersion = "2.8.4" // https://developer.android.com/jetpack/androidx/releases/room
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0") // https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.10.0")
}