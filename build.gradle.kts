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

}
//Secrets Gradle Plugin
buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}
