import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-base")
    id("org.openapi.generator")
}

tasks.withType<KotlinCompile> {
    dependsOn("generateOrderServiceApi")
}

tasks.register("generateOrderServiceApi", GenerateTask::class) {
    group = CUSTOM_TASK_GROUP

    generatorName.set("kotlin-spring")

    inputSpec.set("$rootDir/openapi-interface/order-service-api.yaml")
    outputDir.set("$rootDir/openapi-interface/build/generated-api")

    modelNameSuffix.set("")

    // https://openapi-generator.tech/docs/generators/kotlin-spring/#config-options
    configOptions.set(
        mapOf(
            "artifactId" to "order-service-api-interface",
            "artifactVersion" to "2.0.0",
            "useSpringBoot3" to "true",
            "basePackage" to "com.food.ordering.system.order.service",
            "apiPackage" to "com.food.ordering.system.order.service.api.v1.rest",
            "modelPackage" to "com.food.ordering.system.order.service.api.v1.model",
            "interfaceOnly" to "true",
            "useTags" to "true",
            "beanQualifiers" to "true",
            "delegatePattern" to "true",
            "exceptionHandler" to "false",
            "gradleBuildFile" to "false",
            "enumPropertyNaming" to "original",
            "documentationProvider" to "none",
            "swaggerDocketConfig" to "false",
        ),
    )
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.0")
}
