plugins {
    java
    jacoco
    id("io.freefair.lombok")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

// execute the jacocoTestReport task every time we execute test
plugins.withType<JacocoPlugin> {
    tasks["test"].finalizedBy("jacocoTestReport")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }

}
