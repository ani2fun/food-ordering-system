plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-base")
}

group = "$GRADLE_GROUP.order-service"

dependencies {
    // Shared common classes (valueobjects, base entity, interfaces etc)
    implementation("$GRADLE_GROUP.shared:domain")

    // For Logging
    implementation("org.springframework.boot:spring-boot-starter-logging")
}
