import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-application")
    id("eu.kakde.microservices.copy-boot-jar")
}

group = "$GRADLE_GROUP.order-service"

tasks.withType<BootJar> {
    enabled = true
}

dependencies {
    // SPECIFIC DEPENDENCIES CAN BE DIRECTLY ACCESSED FROM VERSION CATALOG
    // e.g. implementation(libs.keycloak.admin.client)

    // PROJECT

    // IMPORT COMMON SHARED MODULE
    implementation("$GRADLE_GROUP.shared:domain")

    // THIS IS SPRING BOOT APP RUNNER WITH MAIN FUNCTION.
    // HENCE FOLLOWING DEPENDENCIES REQUIRED TO BUILD AND LAUNCH AN SPRING BOOT APPLICATION.
    implementation(project(":data-access"))
    implementation(project(":domain:application-service"))
    implementation(project(":domain:core"))
    implementation(project(":messaging"))
    implementation(project(":rest-api"))

    // RUNTIME

    // DEVELOPMENT
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // IMPLEMENTATION
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //
    runtimeOnly("org.postgresql:postgresql")

    // ANNOTATION PROCESSOR
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // TEST DEPENDENCIES
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    testImplementation(libs.mockito.kotlin)
}
