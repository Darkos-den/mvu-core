import java.util.Date

plugins {
    id("com.android.library")
    kotlin("multiplatform")
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

android {
    val sdkMin = 23
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
}

kotlin {
    android("android") {
        publishLibraryVariants("release")
    }
    ios {
        binaries {
            framework()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
            }
        }
        val androidMain by getting
        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.8")
            }
        }
    }
}

afterEvaluate {
    publishing.publications.all {
            if(this is MavenPublication){
                groupId = artifactGroup

                artifactId = when(name){
                    "metadata" -> artifactName
                    "androidRelease" -> "$artifactName-android"
                    else -> "$artifactName-$name"
                }
            }
        }
}

bintray {
    user = project.property("bintrayUser").toString()
    key = project.property("bintrayApiKey").toString()
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

tasks.getByName<com.jfrog.bintray.gradle.tasks.BintrayUploadTask>("bintrayUpload"){
    doFirst {
        publishing.publications.asMap.keys
            .filter { it != "kotlinMultiplatform" }
            .toTypedArray()
            .let {
                setPublications(*it)
            }
    }
}

tasks.getByName("bintrayUpload").dependsOn(tasks.getByName("publishToMavenLocal").path)