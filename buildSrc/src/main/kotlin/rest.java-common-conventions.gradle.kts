
//Common Configuration for all Java projects (referenced by Java applications and libraries)
plugins {
    java
}

group = "de.jobusam.rest"

repositories {
    mavenCentral()
}

dependencies {

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

// Configure JDK 15 with preview functions in the following section

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_15
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()

    // this doesn't work correctly
    jvmArgs?.add("--enable-preview")
    // only this way o setting the properties works currently
    setJvmArgs(listOf("--enable-preview"))
    //println("jvmArgs = "+jvmArgs)
}

// compile with preview functions tu support data records
tasks.compileJava {
    options.compilerArgs.add("--enable-preview")
}

// run tests with preview functions
tasks.compileTestJava {
    options.compilerArgs.add("--enable-preview")
}
