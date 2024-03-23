plugins {
    id("eu.kakde.microservices.kotlin-library")
}

group = "$GRADLE_GROUP.shared" // THIS GROUPING IS IMPORTANT. OTHERWISE GRADLE WONT BE ABLE TO FIND THIS MODULE.
