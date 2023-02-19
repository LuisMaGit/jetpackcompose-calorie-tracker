plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization")
}

android {
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.applicationId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
    packagingOptions {
        pickFirst("META-INF/AL2.0")
        pickFirst("META-INF/LICENSE.md")
        pickFirst("META-INF/LICENSE-notice.md")
        pickFirst("META-INF/LGPL2.1")
        pickFirst("**/attach_hotspot_windows.dll")
        pickFirst("META-INF/licenses/ASM")
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.coreUI))
    implementation(project(Modules.coreTest))
    implementation(project(Modules.coreData))
    implementation(project(Modules.router))
    implementation(project(Modules.startupUI))
    implementation(project(Modules.onboardingUI))
    implementation(project(Modules.trackerUI))
    implementation(project(Modules.trackerDomain))
    implementation(project(Modules.trackerDI))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)

    implementation(Google.material)

    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.material)
    implementation(Compose.runtime)
    implementation(Compose.activityCompose)
    implementation(Compose.viewModelCompose)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.navigation)
    debugImplementation(Compose.debugImplementation)

    implementation(Image.coil)

    implementation(Ktor.ktorCore)
    implementation(Ktor.ktorAndroidClient)

    implementation(Serialization.kotlinSerializationJsonDependency)
    implementation(SqlDelight.sqlDelightAndroidDriver)

    implementation(DataStore.dataStore)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    testImplementation(Test.junit4)
    testImplementation(Test.mockkAndroid)
    testImplementation(Test.mockkAgent)
    testImplementation(Test.testCoroutines)
    testImplementation(Test.turbine)
}