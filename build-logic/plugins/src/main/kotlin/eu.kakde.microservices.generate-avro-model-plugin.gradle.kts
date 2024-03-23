import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.davidmc24.gradle.plugin.avro")
}

tasks.withType<KotlinCompile> {
    dependsOn("generateAvroModel")
}

tasks.register<GenerateAvroJavaTask>("generateAvroModel") {
    source("${project.layout.projectDirectory}/src/main/resources/avro")
    setOutputDir(file("${project.layout.projectDirectory}/src/main/java"))
    val message =
        """
        ############################################################################
        # 🚀 Generating AVRO model using plugin "com.github.davidmc24.gradle.plugin.avro". 🛠️
        # There are no official Gradle plugins available for generating AVRO models yet.
        # TODO: Optimize this process once an Official Gradle Plugin becomes available for the same task. 🔄
        ############################################################################
        """.trimIndent()
    println(message)
}

avro {
    isCreateSetters.set(true)
    isCreateOptionalGetters.set(false)
    isGettersReturnOptional.set(false)
    isOptionalGettersForNullableFieldsOnly.set(false)
    outputCharacterEncoding.set("UTF-8")
    stringType.set("String")
    isEnableDecimalLogicalType.set(true)
}
