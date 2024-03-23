pluginManagement {
    includeBuild("../build-logic")
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
        maven(url = uri("https://repo1.maven.org/maven2"))
        maven(url = uri("https://packages.confluent.io/maven"))
    }
    versionCatalogs {
        create("libs") {
//            from("eu.kakde.personal.projects.food-ordering-system:version-catalog:1.0.4")
            from(files("../version-catalog-producer/libs.versions.toml"))
        }
    }
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include("kafka-model")
include("kafka-config-data")
include("kafka-consumer")
include("kafka-producer")

rootProject.name = "shared-infra"
