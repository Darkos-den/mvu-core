pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        mavenCentral()
    }
}
rootProject.name = "mvu-core"

enableFeaturePreview("GRADLE_METADATA")

include(":core")

