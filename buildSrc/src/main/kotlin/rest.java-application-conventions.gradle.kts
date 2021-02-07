import gradle.kotlin.dsl.accessors._1e45eae12047fc2ad4d6e1ec9b70a640.implementation

//Common Configuration for Java applications (referenced by client and service)
plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("rest.java-common-conventions")

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

// Common dependencies for logging and data deserialization in application projects
dependencies{
    // With BOM files it's possible to align dependent artifact families with the same or at least a suitable version.
    // define jackson 2.12 for supporting java records in model subproject
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.12.1"))

    // define latest version for log4j
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.14.0"))

    //logging with log4j
    implementation("org.apache.logging.log4j","log4j-api")
    implementation("org.apache.logging.log4j","log4j-core")
    // use slf4j adapter to forward logs to log4j because feign uses slf4j
    implementation("org.apache.logging.log4j","log4j-slf4j-impl")
    // used to read log4j yaml
    implementation("com.fasterxml.jackson.dataformat","jackson-dataformat-yaml")
    // used for data serialization (also for deserializing yaml file)
    implementation("com.fasterxml.jackson.core","jackson-databind")
}

application {
    this.applicationDefaultJvmArgs += "--enable-preview"
}