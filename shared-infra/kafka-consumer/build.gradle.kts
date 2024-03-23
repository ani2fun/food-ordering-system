plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-base")
}

group = "$GRADLE_GROUP.shared-infra"

dependencies {

    implementation("$GRADLE_GROUP.order-service:core")
    implementation(project(":kafka-config-data"))

    implementation(libs.apache.avro)
    implementation(libs.kafka.spring)

    implementation("io.confluent:kafka-avro-serializer:${libs.versions.kafkaAvroSerializerVersion.get()}") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
        exclude(group = "log4j", module = "log4j")
        exclude(group = "io.swagger", module = "swagger-annotations")
        exclude(group = "io.swagger", module = "swagger-core")
    }

    implementation("org.springframework.boot:spring-boot-starter-logging")

    // TEST DEPENDENCIES
    testImplementation(libs.mockito.kotlin)
}
