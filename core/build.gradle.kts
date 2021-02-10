plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

val organization = "darkosinc"
val repository = "MVU"

val artifactName = "core"
val artifactGroup = "com.$organization.$repository"
val artifactVersion = "1.0.0-rc1"

group = artifactGroup
version = artifactVersion

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}

android {
    val sdkMin = 26
    val sdkCompile = 30

    compileSdkVersion(sdkCompile)
    defaultConfig {
        minSdkVersion(sdkMin)
        targetSdkVersion(sdkCompile)
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
}

kotlin {
    android("android") {
        publishAllLibraryVariants()
    }
    ios {
        binaries {
            framework {
                baseName = artifactName
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
            }
        }
        val androidMain by getting
        val iosMain by getting
    }
}