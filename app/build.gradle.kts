import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.google.dagger.hilt.android")
    id ("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

val localProperties = Properties()
file("../local.properties").takeIf { it.exists() }?.inputStream()?.use { localProperties.load(it) }

android {
    namespace = "com.example.getirlite"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.getirlite"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val webClientId = localProperties.getProperty("DEFAULT_WEB_CLIENT_ID") ?: "\"\""
        buildConfigField("String", "DEFAULT_WEB_CLIENT_ID", "\"$webClientId\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = localProperties.getProperty("KEY_ALIAS")
            keyPassword = localProperties.getProperty("KEY_PASSWORD")
            storeFile = file(localProperties.getProperty("STORE_FILE"))
            storePassword = localProperties.getProperty("STORE_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
    implementation(composeBom)
    implementation(libs.material3)
    implementation(libs.androidx.foundation)
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    implementation (libs.android.lottie.compose)

    implementation(libs.coil3.coil.compose)
    implementation (libs.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    implementation (libs.hilt.android)
    implementation(libs.googleid)
    debugImplementation(libs.androidx.ui.test.manifest)
    kapt (libs.hilt.compiler.v2432)
    kapt (libs.androidx.hilt.compiler)


    implementation (libs.lottie)

    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)

    implementation (libs.coil)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    implementation (libs.androidx.core.splashscreen)
    implementation (libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    androidTestImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
}