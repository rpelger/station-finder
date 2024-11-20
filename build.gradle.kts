plugins {
    application
}

group = "com.comsystoreply.labs"
version = "0.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.3.0")
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
    testLogging.events("STANDARD_OUT", "FAILED", "PASSED")
    testLogging.showExceptions = true
    testLogging.showStandardStreams = true
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "com.comsystoreply.labs.chargingstations.Main"
}

