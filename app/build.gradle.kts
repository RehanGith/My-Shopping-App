plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger)
    kotlin("kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.my_shoppings"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.my_shoppings"
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
    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }
}

dependencies {
    //dagger dependencies
    implementation(libs.dagger.hilt)
    implementation(libs.firebase.firestore)
    kapt(libs.dagger.kapt)
    //Glide dependencies
    implementation(libs.github.glide)
    //retrofits
    implementation(libs.android.retrofit.json)
    implementation(libs.android.retrofit2)
    //logging intercepter
    implementation(libs.android.logging.interceptor)
    //lifecycle
    implementation(libs.androidx.lifecyle.viewmodel)
    //navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    //firebase auth
    implementation(libs.androidx.firebase)
    //loading button
    implementation(libs.androidx.loading.button)
    //viewpager
    implementation(libs.github.viewpager)
    //circleimage
    implementation(libs.shuhart.stepview)
    implementation(libs.jetbrains.stdlib)
    implementation(libs.androidx.circular.image)

    //coroutine
    implementation(libs.jetbrains.coroutine.core)
    implementation(libs.jetbrains.coroutine.kotlinx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}