plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    // Secrets gradle plugin
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    // Kotlin Kapt plugin
    id("kotlin-kapt")
    // Kotlin Symbol Processing plugin
    id("com.google.devtools.ksp")
    // Hilt dependency Injection plugin
    id("com.google.dagger.hilt.android")
    // Safe args plugin Kotlin-Only
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.ragdoll.newsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ragdoll.newsapp"
        minSdk = 26
        targetSdk = 35
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
        viewBinding = true
        buildConfig = true
    }

}
secrets {
    // To add your Maps API key to this project:
    // 1. If the secrets.properties file does not exist, create it in the same folder as the local.properties file.
    // 2. Add this line, where YOUR_API_KEY is your API key:
    //        MAPS_API_KEY=YOUR_API_KEY
    propertiesFileName = "secrets.properties"

    // A properties file containing default secret values. This file can be
    // checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"

    ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
    ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}
dependencies {

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // Gson Converter
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // OkHttp Logging Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Admob
    implementation("com.google.android.gms:play-services-ads:24.2.0")
    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    // Firebase Authentication library
    implementation("com.google.firebase:firebase-auth")

    /*MVVM + Clean Architecture*/
    // Serialized Name
    implementation("com.google.code.gson:gson:2.13.1")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.0")
    // Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:2.9.0")
    // Hilt Dependency Injection
    implementation("com.google.dagger:hilt-android:2.56.2")
    // Kotlin Symbol Processing for Hilt
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")
    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:2.9.0")
    implementation("androidx.navigation:navigation-ui:2.9.0")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}