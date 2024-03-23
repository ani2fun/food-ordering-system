// == Define locations for build logic ==
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven { url = uri("https://repo.spring.io/milestone") }
    }
    includeBuild("../build-logic")
}

// == Define locations for components ==
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
includeBuild("../platforms")
includeBuild("../shared")
includeBuild("../shared-infra")

include("app-container")
include("domain:core")
include("domain:application-service")
include("data-access")
include("messaging")
include("openapi-interface")
include("rest-api")
// == Define the inner structure of this component ==
rootProject.name = "order-service" // the component name
