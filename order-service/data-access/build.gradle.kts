plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-base")
}

group = "$GRADLE_GROUP.order-service"

dependencies {
    implementation("$GRADLE_GROUP.shared:domain")

    // Implementing interfaces from domain layer
    implementation(project(":domain:application-service"))
    implementation(project(":domain:core"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-logging")

    runtimeOnly("org.postgresql:postgresql")

    // TEST DEPENDENCIES
    testImplementation(libs.mockito.kotlin)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
