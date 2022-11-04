plugins {
    id("java")
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("java-conventions")
    id("java-library")
}

tasks.bootJar { enabled = true }
tasks.jar { enabled = false }

springBoot {
    buildInfo()
}

dependencies {
    implementation(platform("com.essentialprogramming.platform:platform"))
    annotationProcessor(platform("com.essentialprogramming.platform:platform"))

    implementation(project(":essentialprogramming-base"))
    runtimeOnly(project(":essentialprogramming-base"))

    implementation(project(":essentialprogramming-util"))
    runtimeOnly(project(":essentialprogramming-util"))

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.mapstruct:mapstruct-processor")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.mapstruct:mapstruct")
    implementation("org.springdoc:springdoc-openapi-ui")
    implementation("org.liquibase:liquibase-core")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("com.h2database:h2:2.1.210")
    testRuntimeOnly("com.h2database:h2:2.1.210")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.google.guava:guava:31.1-jre")
}
