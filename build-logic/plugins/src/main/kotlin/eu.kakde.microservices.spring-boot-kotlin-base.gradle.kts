import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("eu.kakde.microservices.kotlin-library")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.jpa")
}

// Enable Jar creation when spring boot base plugin is applied. Disabling it will throw following error during compilation:
tasks.named<Jar>("jar") {
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

tasks.named<BootJar>("bootJar") {
    enabled = false
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
    enabled = false
    when {
        enabled -> {
            logger.lifecycle("Enabling '$name' task in project ${project.name}.")
        }
        else -> {
            logger.lifecycle("'$name' task is not enabled in project ${project.name}.")
        }
    }
}
