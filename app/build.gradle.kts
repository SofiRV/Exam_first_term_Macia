plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.exam_macia_first_term"
    compileSdk = 34  // ✅ CORREGIDO (era "36" con sintaxis extraña)

    defaultConfig {
        applicationId = "com.example.exam_macia_first_term"
        minSdk = 24
        targetSdk = 34  // ✅ CORREGIDO (era 36)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules. pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17  // ✅ ACTUALIZADO a 17
        targetCompatibility = JavaVersion.VERSION_17  // ✅ ACTUALIZADO a 17
    }
}

dependencies {
    // ✅ USANDO VERSION CATALOGS (libs.*)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}