plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.g7s21_ddim_proyecto_fabian"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.g7s21_ddim_proyecto_fabian"
        minSdk = 27
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("com.android.volley:volley:1.2.1") //  manejar las solicitudes HTTP

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation(libs.support.annotations)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}