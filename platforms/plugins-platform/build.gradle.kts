plugins {
    id("java-platform")
    `maven-publish`
}

group = "eu.kakde.microservices.platform"

dependencies {
    constraints {
        api(libs.kotlin.jvm.gradle.plugin)
        api(libs.spring.boot.gradle.plugin)
        api(libs.openapi.generator.gradle.plugin)
        api(libs.ktlint.plugin)
        api(libs.avro.generator.gradle.plugin)
    }
}

// PUBLISHING CONFIG
object Meta {
    val GROUP = "eu.kakde.microservices.platform"
    val ARTIFACT_ID = "plugins-platform"
    val VERSION = "1.0.0"
    val DESC = "GitHub Version Catalog Repository for Personal Projects based on Gradle"
    val LICENSE = "Apache-2.0"
    val LICENSE_URL = "https://opensource.org/licenses/Apache-2.0"
    val GITHUB_REPO = "microservices/microservices-platform.git"
    val DEVELOPER_ID = "Europcar developer"
    val DEVELOPER_NAME = "Europcar developer"
    val DEVELOPER_ORGANIZATION = "embog.com"
    val DEVELOPER_ORGANIZATION_URL = "https://www.europcar.com"
}

publishing {
    publications {
        create<MavenPublication>("microservice-plugins-platform") {
            from(components["javaPlatform"])
            groupId = Meta.GROUP
            artifactId = Meta.ARTIFACT_ID
            version = Meta.VERSION

            pom {
                name.set(Meta.ARTIFACT_ID)
                description.set(Meta.DESC)
                url.set("https://github.com/${Meta.GITHUB_REPO}")
                licenses {
                    license {
                        name.set(Meta.LICENSE)
                        url.set(Meta.LICENSE_URL)
                    }
                }
                developers {
                    developer {
                        id.set(Meta.DEVELOPER_ID)
                        name.set(Meta.DEVELOPER_NAME)
                        organization.set(Meta.DEVELOPER_ORGANIZATION)
                        organizationUrl.set(Meta.DEVELOPER_ORGANIZATION_URL)
                    }
                }
                scm {
                    url.set("https://github.com/${Meta.GITHUB_REPO}")
                    connection.set("scm:git:https://github.com/${Meta.GITHUB_REPO}")
                    developerConnection.set("scm:git:https://github.com/${Meta.GITHUB_REPO}")
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/${Meta.GITHUB_REPO}/issues")
                }
            }
        }
    }
}
