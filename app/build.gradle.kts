import java.io.FileInputStream
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

android {
    namespace = "com.example.getirlite"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.getirlite"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val secretsPropertiesFile = rootProject.file("secrets.properties")
        val secretsProperties = Properties()
        if (secretsPropertiesFile.exists()) {
            secretsProperties.load(FileInputStream(secretsPropertiesFile))
        }


        buildConfigField(
            "String",
            "DEFAULT_WEB_CLIENT_ID",
            "\"${secretsProperties["DEFAULT_WEB_CLIENT_ID"]}\""
        )

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

    kotlin {
        jvmToolchain(17)
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    implementation (libs.hilt.android)
    implementation(libs.googleid)
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