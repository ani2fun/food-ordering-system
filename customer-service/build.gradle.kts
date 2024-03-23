import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-application")
    id("eu.kakde.microservices.copy-boot-jar")
}

group = "$GRADLE_GROUP.customer-service"

tasks.withType<BootJar> {
    enabled = true
}

dependencies {
    // IMPORT COMMON SHARED MODULE
    implementation("$GRADLE_GROUP.shared:domain")

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
