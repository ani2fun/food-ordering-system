plugins {
    id("java-platform")
    `maven-publish`
}

group = "eu.kakde.microservices.platform"

// allow the definition of dependencies to other platforms like the JUnit 5 BOM
javaPlatform.allowDependencies()

dependencies {
    api(platform("org.junit:junit-bom:${libs.versions.junitVersion.get()}"))

    constraints {
        api(libs.junit.jupiter.api)
        api(libs.junit.jupiter.engine)
        api(libs.junit.jupiter.params)
        api(libs.mockito.kotlin)
    }
}

// PUBLISHING CONFIG
object Meta {
    val GROUP = "eu.kakde.microservices.platform"
    val ARTIFACT_ID = "test-platform"
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
        create<MavenPublication>("microservice-test-platform") {
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
