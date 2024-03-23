### Sample Project (WIP)

The tech stack: Spring Boot, Kotlin, Gradle (Kotlin Dsl), PostgreSQL, Kafka.

A sample project to practice and implement DDD, Hexagonal Architecture In Kotlin Spring Boot and with Modern gradle practices, based on various online MOOC tutorials.

Thanks to the creator of Mooc course for the example given in Java and Maven build tool. [Mooc course](https://www.udemy.com/course/microservices-clean-architecture-ddd-saga-outbox-kafka-kubernetes/) 

### Please set up the necessary components such as PostgreSQL, Zookeeper, and Kafka to facilitate testing for this project.
[DevOps Readme](./devops/docker-compose/ZREADME.md)

#### Build avro model

Go to directory first "kafka-model" and do mvn install to generate avro model java classes.
```bash
cd ./shared-infra/kafka-model && mvn clean install
```

If you need to produce your own version catalog then, add `gradle.properties` file in `version-catalog-producer`, with following configuration:
```console
# last 6 letters from gpg signing key
signing.keyId=<ABCDEFG>
# Passphrase for GPG
signing.password=<PASSPHRASE>
# Path to your secring.gpg file.
signing.secretKeyRingFile=/Users/jondoe/.gnupg/secring.gpg

sonatypeUsername=<SONATYPE_USERNAME>
sonatypePassword=<SONATYPE_PASSWORD>
```

#### For GPG key creation steps, refer to: [GPG Keys - Guide](./notes/markdowns/GPGKeysGuide.md):

### Some useful commands

#### Check projects inside specific composite build

```bash
./gradlew :order-service:projects

Project ':order-service'
+--- Project ':order-service:app-container'
+--- Project ':order-service:data-access'
+--- Project ':order-service:domain'
|    +--- Project ':order-service:domain:application-service'
|    \--- Project ':order-service:domain:core'
+--- Project ':order-service:messaging'
+--- Project ':order-service:openapi-interface'
\--- Project ':order-service:rest-api'

Included builds
+--- Included build ':build-logic'
+--- Included build ':platforms'
+--- Included build ':shared'
+--- Included build ':shared-infra'
+--- Included build ':order-service'
\--- Included build ':version-catalog-producer'
```

#### Build
```
./gradlew :shared:common-domain:build

./gradlew :order-service:app-container:build
```

#### Run as springboot app:
```
./gradlew :order-service:clean && ./gradlew :order-service:app-container:bootRun
```

### RUN AS SPRING BOOT JAR
```bash
./gradlew :order-service:app-container:bootJar
```
```bash
java -jar ./order-service/app-container/build/libs/app-container-1.0.0.jar
```

#### Other MacOS specific commands:
- Removing all build dirs manually  
```bash
find . -name "build" -exec rm -rf {} \; && find . -name ".gradle" -exec rm -rf {} \; 
```

- Rename .java file to .kt in current dir.
```bash
find ./ -type f -name "*.java" -exec sh -c 'mv "$0" "${0%.java}.kt"' {} \;
```