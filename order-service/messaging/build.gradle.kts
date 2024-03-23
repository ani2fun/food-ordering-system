plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-base")
}

group = "$GRADLE_GROUP.order-service"

dependencies {

    // Implementing interfaces from domain layer
    implementation(project(":domain:application-service"))
    implementation(project(":domain:core"))

    implementation("$GRADLE_GROUP.shared:domain")
    implementation("com.food.ordering.system:kafka-model:1.0.0")
    implementation("$GRADLE_GROUP.shared-infra:kafka-producer")
    implementation("$GRADLE_GROUP.shared-infra:kafka-consumer")

    implementation(libs.kafka.spring)
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // TEST DEPENDENCIES
    testImplementation(libs.mockito.kotlin)
}
