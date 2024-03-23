plugins {
    id("java-platform")
    `maven-publish`
}

group = "eu.kakde.microservices.platform"

// allow the definition of dependencies to other platforms like the Spring Boot BOM
javaPlatform.allowDependencies()

val libraries = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:${libs.versions.springBootVersion.get()}"))
    api(platform("org.springframework.cloud:spring-cloud-dependencies:${libs.versions.springCloudVersion.get()}"))
    api(platform("org.junit:junit-bom:${libs.versions.junitVersion.get()}"))

    constraints {
        libraries.libraryAliases.map {
            api(libraries.findLibrary(it).get())
        }
    }
}

// PUBLISHING CONFIG
object Meta {
    val GROUP = "eu.kakde.microservices.platform"
    val ARTIFACT_ID = "microservices-platform"
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
        create<MavenPublication>("microservices-platform") {
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
