// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false
    //  Hilt dependency Injection
    id("com.google.dagger.hilt.android") version "2.56.2" apply false
    // Kotlin Kapt plugin
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    // Kotlin serialization plugin for type safe routes and navigation arguments
    kotlin("plugin.serialization") version "2.0.21"

}

buildscript {
    repositories {
        google()
    }
    dependencies {
        // Secrets Gradle Plugin
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
        // Navigation Safe Args Gradle Plugin
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.0")
    }
}
