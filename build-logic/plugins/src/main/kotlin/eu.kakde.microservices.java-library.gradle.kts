plugins {
    // Apply all tasks from commons. Apart from this if something more needed specific for java-library apply it here.
    id("eu.kakde.microservices.commons-base")

    `java-library`
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
