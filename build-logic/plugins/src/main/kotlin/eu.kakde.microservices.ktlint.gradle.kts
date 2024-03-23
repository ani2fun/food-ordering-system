plugins {
    id("org.jlleitschuh.gradle.ktlint")
}

ktlint {
    filter {
        exclude("**/generated/**")
        include("*.kts", "*/*.kts", "**/kotlin/**")
    }
}
