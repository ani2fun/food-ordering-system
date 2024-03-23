plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-base")
}

group = "$GRADLE_GROUP.shared-infra"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-logging")

    // TEST DEPENDENCIES
    testImplementation(libs.mockito.kotlin)
}
