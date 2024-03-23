plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-base")
}

group = "$GRADLE_GROUP.order-service"

dependencies {
    // PROJECT DEPENDENCIES
    implementation("$GRADLE_GROUP.shared:domain")
    implementation(project(":domain:core"))

    // SPRING DEPENDENCIES
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework:spring-tx")

    // TEST DEPENDENCIES
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    // TEST DEPENDENCIES
    testImplementation(libs.mockito.kotlin)
}
