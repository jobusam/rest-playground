plugins {
    id("rest.java-application-conventions")
}

version = "1.0-SNAPSHOT"

dependencies {
    // use shared data model
    implementation(project(":model"))

    // feign adapter using latest version 10.12
    // keep in mind version 11 is not a correct release
    implementation(platform("io.github.openfeign:feign-bom:10.12"))
    implementation("io.github.openfeign","feign-core")
    implementation("io.github.openfeign","feign-jackson")
    implementation("io.github.openfeign","feign-okhttp")
    implementation("io.github.openfeign","feign-slf4j")
}

application {
    mainClass.set("de.jobusam.rest.client.RestClient")
}

