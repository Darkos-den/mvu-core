import org.jetbrains.kotlin.konan.properties.loadProperties
import kotlin.io.println

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
    id("com.jfrog.artifactory")
}

val organization = "darkos"
val repository = "mvu"

val artifactName = "core"
val artifactGroup = "com.$organization.$repository"
val artifactVersion = "1.0.0-rc2"

val repoName = "mvu-core"

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

val mUsername: String? by localProperties
val mPassword: String? by localProperties

artifactory {
    val baseUrl = "https://darkos.jfrog.io/artifactory"

    setContextUrl(baseUrl)   //The base Artifactory URL if not overridden by the publisher/resolver

    publish(closureOf<org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig> {
        setContextUrl(baseUrl)

        repository(closureOf<org.jfrog.gradle.plugin.artifactory.dsl.DoubleDelegateWrapper> {
            invokeMethod("setRepoKey", repoName)
            invokeMethod("setUsername", mUsername)
            invokeMethod("setPassword", mPassword)
        })
    })

    resolve(closureOf<org.jfrog.gradle.plugin.artifactory.dsl.ResolverConfig> {
        repository(closureOf<org.jfrog.gradle.plugin.artifactory.dsl.DoubleDelegateWrapper> {
            invokeMethod("setRepoKey", "mvu")
            invokeMethod("setUsername", mUsername)
            invokeMethod("setPassword", mPassword)
            invokeMethod("setMaven", true)
        })
    })
}

publishing {
    val vcs = "https://darkos.jfrog.io/artifactory/$repoName/"

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

    repositories {
        maven {
            name = "artifactory"
            url =
                uri("https://darkos.jfrog.io/artifactory/$repoName/")
            credentials {
                username = mUsername
                password = mPassword
            }
        }
    }
}