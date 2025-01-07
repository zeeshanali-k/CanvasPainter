rootProject.name = "CanvasPainterRoot"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jogamp.org/deployment/maven")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jfrog.anythinktech.com/artifactory/overseas_sdk")
    }
}

dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()

        mavenCentral()
        maven("https://jogamp.org/deployment/maven")
        maven("https://jfrog.anythinktech.com/artifactory/overseas_sdk")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

    }
}

include(":CanvasPainter")
include(":sample")

