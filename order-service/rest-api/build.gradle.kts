plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-base")
}
group = "$GRADLE_GROUP.order-service"

sourceSets {
    main {
        kotlin {
            srcDirs(
                "${findProject(":openapi-interface")
                    ?.layout
                    ?.buildDirectory
                    ?.get()}/generated-api/src/main/kotlin"
                    .trimIndent(),
            )
        }
    }
}

dependencies {
    // INTERNAL PROJECT DEPENDENCIES
    implementation("$GRADLE_GROUP.shared:domain")

    implementation(project(":openapi-interface"))

    implementation(project(":domain:application-service"))

    implementation(project(":domain:core"))

    // SPRING
    implementation(libs.google.code.findbugs)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("jakarta.annotation:jakarta.annotation-api")

    // TEST DEPENDENCIES
    testImplementation(libs.mockito.kotlin)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
