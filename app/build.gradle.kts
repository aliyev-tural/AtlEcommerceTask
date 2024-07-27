plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.ecommercetask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ecommercetask"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    val nav_version = "2.7.7"
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //coil
    implementation(libs.coil)

    //retrofit
    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.gson)


    //logging interceptor
    implementation(libs.logging.interceptor)

    //coroutines
    implementation(libs.kotlinx.coroutines.android)

    //lifecycles
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //dagger hilt

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))

    // Add the dependency for the Firebase Authentication library
    implementation(libs.firebase.auth)
    implementation ("com.google.firebase:firebase-firestore:24.0.2")
    implementation ("com.google.firebase:firebase-auth:21.0.1")
    implementation ("com.google.android.gms:play-services-auth:19.2.9")

    // Also add the dependency for the Google Play services library and specify its version
    implementation(libs.play.services.auth)

    implementation(libs.androidx.hilt.navigation.fragment)
}