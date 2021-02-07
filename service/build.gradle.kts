plugins {
    id("rest.java-application-conventions")
}

version = "1.0-SNAPSHOT"


dependencies {
    implementation(project(":model"))
    implementation("io.javalin", "javalin", "3.13.0")

    // version is derived from application conventions
    implementation("com.fasterxml.jackson.core", "jackson-core")
}

application {
    mainClass.set("de.jobusam.rest.service.PodcastService")
}