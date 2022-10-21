plugins {
    id("java")
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("java-conventions")
    id("maven-publish")
    id("java-library")
}

//tasks.bootJar { enabled = true }
tasks.jar { enabled = false }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

repositories {
    mavenCentral()
}
