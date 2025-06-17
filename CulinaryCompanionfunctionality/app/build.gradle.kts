plugins {
    alias(libs.plugins.android.application)
//    id("com.google.devtools.ksp")
}

android {
    namespace = "com.cc.culinarycompanion"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cc.culinarycompanion"
        minSdk = 24
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

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation (libs.core.testing)
    androidTestImplementation (libs.room.testing)

    // circle image view
    implementation (libs.circleimageview)

    // fragment navigation
    implementation (libs.navigation.fragment)
    implementation (libs.navigation.ui)

    // below dependency for using room.
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // add below dependency for using lifecycle extensions for room.
    implementation (libs.lifecycle.extensions)
    annotationProcessor (libs.lifecycle.compiler)
}