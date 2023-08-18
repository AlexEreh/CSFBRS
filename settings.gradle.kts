pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.4.0")
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
rootProject.name = "CSFBRS"
include(":app")
include(":data:grades")
include(":feature:stats")
include(":feature:profile")
include(":common:ui")
include(":feature:login")
include(":common:datastore")
include(":common:model")
include(":common:database")
include(":common:util")
include(":common:network")
