import gradle.kotlin.dsl.accessors._8c47cae829ea3d03260d5ff13fb2398e.implementation
import gradle.kotlin.dsl.accessors._8c47cae829ea3d03260d5ff13fb2398e.testImplementation
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    id("java")
}

// IMPORTANT: DEFINE GRADLE BUILD GROUP FOR PROJECT BUILDING AND SHARING DEPENDENCIES AMONG PROJECTS
group = GRADLE_GROUP
version = PROJECT_VERSION

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_17.toString())
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = CHARACTER_ENCODING
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:all",
            /**
             * TODO: Document the purpose of this configuration.
             * If activated, the final build will fail in Kafka-Model due to warning error.
             * "-Werror",
             *
             * Example warning:
             * /Users/aniket/Development/kotlinprojects/food-ordering-system/shared-infra/kafka/kafka-model/src/main/java/com/food/ordering/system/kafka/order/avro/model/RestaurantApprovalRequestAvroModel.java:85:
             * warning: [serial] non-transient instance field of a serializable class declared with a non-serializable type
             *   private java.util.List<com.food.ordering.system.kafka.order.avro.model.Product> products;
             */
        ),
    )
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
    testLogging {
        events = setOf(TestLogEvent.FAILED)
        exceptionFormat = TestExceptionFormat.FULL
        events("passed", "skipped", "failed")
    }
}

dependencies {
    // PLATFORM project ":platforms"
    implementation(platform("eu.kakde.microservices.platform:microservices-platform"))
    implementation(platform("eu.kakde.microservices.platform:plugins-platform"))
    testImplementation(platform("eu.kakde.microservices.platform:test-platform"))

    // OTHER
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
