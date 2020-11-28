import java.util.Date

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("base")
    id("maven-publish")
    id("com.jfrog.bintray")
}

val organization = "darkosinc"
val repository = "MVU"

val artifactName = "core"
val artifactGroup = "com.$organization.$repository"
val artifactVersion = "0.0.1"

group = artifactGroup
version = artifactVersion

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
kotlin {
    android {
        publishAllLibraryVariants()
    }
    ios {
        binaries {
            framework {
                baseName = "mvu-core"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.8")
            }
        }
    }
}
android {
    val sdkMin = 23
    val sdkCompile = 30

    compileSdkVersion(sdkCompile)
    sourceSets["main"].setRoot("src/androidMain")
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].java.srcDirs("src/androidMain/kotlin")
    defaultConfig {
        minSdkVersion(sdkMin)
        targetSdkVersion(sdkCompile)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

bintray {
    user = project.findProperty("bintrayUser").toString()
    key = project.findProperty("bintrayKey").toString()
    publish = false

    pkg.apply {
        repo = repository
        name = artifactName
        userOrg = organization

        version.apply {
            name = artifactVersion
            released = Date().toString()
            vcsTag = artifactVersion
        }
    }
}