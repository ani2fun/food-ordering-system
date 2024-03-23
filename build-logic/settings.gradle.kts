pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven(url = uri("https://repo1.maven.org/maven2"))
        maven(url = uri("https://packages.confluent.io/maven"))
    }
    versionCatalogs {
        val libs by creating {
            from(files("../version-catalog-producer/libs.versions.toml"))
//            from("eu.kakde.personal.projects.food-ordering-system:version-catalog:1.0.4")
        }
    }
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

includeBuild("../platforms")

include("plugins")

rootProject.name = "build-logic"
