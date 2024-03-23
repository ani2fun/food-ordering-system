import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("eu.kakde.microservices.spring-boot-kotlin-base")
}

springBoot {
    // Creates META-INF/build-info.properties for Spring Boot Actuator
    buildInfo()
}

// Enable bootJar and bootRun tasks
tasks.named<BootJar>("bootJar") {
    enabled = true
    when {
        enabled -> {
            logger.lifecycle("Enabling '$name' task in project ${project.name}.")
        }
        else -> {
            logger.lifecycle("'$name' task is not enabled in project ${project.name}.")
        }
    }
}

tasks.named<BootRun>("bootRun") {
    enabled = true
    when {
        enabled -> {
            logger.lifecycle("Enabling '$name' task in project ${project.name}.")
        }
        else -> {
            logger.lifecycle("'$name' task is not enabled in project ${project.name}.")
        }
    }
}
