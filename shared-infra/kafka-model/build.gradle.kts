plugins {
    id("eu.kakde.microservices.java-library")
    id("eu.kakde.microservices.spring-boot-kotlin-base")
}

group = "$GRADLE_GROUP.shared-infra"

dependencies {
    implementation(libs.apache.avro)
    implementation("io.confluent:kafka-avro-serializer:${libs.versions.kafkaAvroSerializerVersion.get()}") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
        exclude(group = "log4j", module = "log4j")
        exclude(group = "io.swagger", module = "swagger-annotations")
        exclude(group = "io.swagger", module = "swagger-core")
    }
}
