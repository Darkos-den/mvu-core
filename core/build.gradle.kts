import org.jetbrains.kotlin.konan.properties.loadProperties
import kotlin.io.println

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

val organization = "darkosinc"
val repository = "MVU"

val artifactName = "core"
val artifactGroup = "com.$organization.$repository"
val artifactVersion = "0.0.9"

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

val localPropsFile: File = project.rootProject.file("local.properties")
val localProperties = loadProperties(localPropsFile.absolutePath)

publishing {
    val vcs = "https://github.com/Darkos-den/mvu-core"

    publications.filterIsInstance<MavenPublication>().forEach { publication ->
        println(publication.name)

        publication.pom {
            name.set(artifactName)
            description.set(project.description)
            url.set(vcs)

            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("repo")
                }
            }
            scm {
                url.set(vcs)
                tag.set(project.version.toString())
            }
        }
    }

    val bintrayUser: String? by localProperties
    val bintrayApiKey: String? by localProperties

    if (bintrayUser != null && bintrayApiKey != null) {
        repositories {
            maven {
                name = "bintray"
                url =
                    uri("https://api.bintray.com/maven/darkosinc/$repository/$artifactName/;publish=1;override=1")
                credentials {
                    username = bintrayUser
                    password = bintrayApiKey
                }
            }
        }
    } else throw IllegalStateException("bintray data not found")
}