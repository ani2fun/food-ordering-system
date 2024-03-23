import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("eu.kakde.microservices.commons-base")
    id("org.springframework.boot")
}

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
