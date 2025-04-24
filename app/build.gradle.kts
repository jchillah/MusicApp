plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "de.syntax_institut.musicapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "de.syntax_institut.musicapp"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    buildToolsVersion = "35.0.0"
    ndkVersion = "28.0.13004108"
}

    dependencies {
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.activity.compose)
        implementation(libs.coil.compose)

        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.compose)

        implementation(libs.kotlinx.coroutines.android)

        implementation(libs.androidx.activity.ktx)
        implementation(libs.androidx.media3.exoplayer)

        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        implementation(libs.androidx.compose.material.icons.extended)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.ui.tooling.preview)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        debugImplementation(libs.androidx.ui.test.manifest)
        implementation(libs.androidx.navigation.compose)

        implementation(libs.kotlinx.serialization)

        implementation (libs.androidx.runtime)
        implementation (libs.androidx.lifecycle.runtime.ktx.v262)

    }