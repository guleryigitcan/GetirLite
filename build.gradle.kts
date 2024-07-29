// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")

    }
}
true // Needed to make the Suppress annotation work for the plugins block