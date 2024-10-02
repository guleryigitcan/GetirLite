// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    dependencies {
        classpath (libs.hilt.android.gradle.plugin)
        classpath (libs.androidx.navigation.safe.args.gradle.plugin)

    }
}
true // Needed to make the Suppress annotation work for the plugins block