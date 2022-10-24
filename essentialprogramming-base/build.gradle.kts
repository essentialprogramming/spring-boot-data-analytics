plugins {
  id("java")
  id("org.springframework.boot") version "2.7.4"
  id("io.spring.dependency-management") version "1.0.14.RELEASE"
  id("java-conventions")
  id("maven-publish")
  id("java-library")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }

tasks.withType<JavaCompile> {
  options.compilerArgs.add("-AddGenerationDate=true")
}

dependencies {

  implementation(platform("com.essentialprogramming.platform:platform"))
  annotationProcessor(platform("com.essentialprogramming.platform:platform"))

  implementation(project(":essentialprogramming-util"))
  runtimeOnly(project(":essentialprogramming-util"))

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.mapstruct:mapstruct-processor")
  annotationProcessor("org.hibernate:hibernate-jpamodelgen:5.6.9.Final")

  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
  implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.2")
  implementation("org.assertj:assertj-core:3.22.0")
  implementation("org.mapstruct:mapstruct")

  implementation("org.springframework.boot:spring-boot-starter-jooq")

  testRuntimeOnly("com.h2database:h2:2.1.210")

  testImplementation("org.springframework.boot:spring-boot-starter-test")

}