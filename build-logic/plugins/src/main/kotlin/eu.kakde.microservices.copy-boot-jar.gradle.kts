tasks.register("copyBuildJar") {
    group = "My Custom Group"
    description = "Copy bootJar from $project to the root build directory"

    doLast {
//        val bootJarFile =
//            project.layout.buildDirectory.get()
//                .dir("libs").asFileTree.files.singleOrNull { it.name == "app-container-${}.jar" }
//                ?: throw GradleException("Cannot find the infrastructure jar file.")
//
//        val targetDir = rootDir.resolve("build").resolve("libs")
//        val rename = "${rootProject.name}-$PROJECT_VERSION.jar"
//
//        copy {
//            from(bootJarFile)
//            into(targetDir)
//            rename { rename }
//        }
    }
}
