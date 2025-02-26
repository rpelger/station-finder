plugins {
    application
    id("gg.jte.gradle") version("3.1.15")
}

group = "com.comsystoreply.labs"
version = "0.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.4.0")
    implementation("io.javalin:javalin-rendering:6.3.0")
    implementation("gg.jte:jte:3.1.15")
    implementation("io.badgod:jayreq:0.0.5")
    implementation("commons-codec:commons-codec:1.17.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.hamcrest:hamcrest-library:2.2")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging.events("FAILED")
    testLogging.showExceptions = true
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

jte {
    precompile()
}

application {
    mainClass = "com.comsystoreply.labs.stationfinder.Main"
}

tasks.precompileJte {
    targetDirectory = project.layout.buildDirectory.dir("jte-classes").get().asFile.toPath()
}
