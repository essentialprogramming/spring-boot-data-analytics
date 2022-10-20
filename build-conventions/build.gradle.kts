plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.springframework.boot:org.springframework.boot.gradle.plugin:2.7.3")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.13.RELEASE")
    implementation("org.owasp:dependency-check-gradle:7.2.0")
    implementation("io.freefair.lombok:io.freefair.lombok.gradle.plugin:6.5.1")
    implementation("com.google.cloud.tools.jib:com.google.cloud.tools.jib.gradle.plugin:3.2.0")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.3")
    implementation("com.vanniktech:gradle-maven-publish-plugin:0.19.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}
