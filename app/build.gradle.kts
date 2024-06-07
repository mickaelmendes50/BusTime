plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "co.mesquita.labs.bustime"
    compileSdk = 34

    defaultConfig {
        applicationId = "co.mesquita.labs.bustime"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Wear Compose
    implementation("androidx.wear.compose:compose-foundation:1.3.1")
    implementation("androidx.wear.compose:compose-material:1.3.1")
    implementation("androidx.wear.compose:compose-navigation:1.3.1")
    implementation("androidx.wear.compose:compose-ui-tooling:1.3.1")
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.wear.tiles:tiles-material:1.3.0")
    implementation("com.google.android.horologist:horologist-compose-tools:0.4.8")
    implementation("com.google.android.horologist:horologist-tiles:0.4.8")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("androidx.wear.watchface:watchface-complications-data-source-ktx:1.2.1")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    implementation("androidx.wear:wear-input:1.2.0-alpha02")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("androidx.compose.material:material-icons-core:1.7.0-alpha05")
    implementation("androidx.compose.material:material-icons-extended:1.7.0-alpha05")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")
    runtimeOnly("com.google.android.horologist:horologist-base-ui:0.4.17")
    runtimeOnly("com.google.android.horologist:horologist-compose-layout:0.6.4")
    runtimeOnly("com.google.android.horologist:horologist-compose-material:0.6.4")
    implementation("com.google.android.horologist:horologist-compose-material:0.6.4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
