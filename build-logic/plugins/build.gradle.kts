plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    // HERE YOU ARE ONLY DECLARING PLATFORM CONSTRAINT
    implementation(platform("eu.kakde.microservices.platform:plugins-platform"))
    implementation(platform("eu.kakde.microservices.platform:test-platform"))
    implementation(platform("eu.kakde.microservices.platform:microservices-platform"))

    catalog.libraryAliases.map {
        implementation(catalog.findLibrary(it).get())
    }
}
