plugins {
  id("org.springframework.boot") version "2.7.4"
  id("io.spring.dependency-management") version "1.0.14.RELEASE"
  id("java-conventions")
  id("maven-publish")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }

dependencies {

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4")
  implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.4")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.4")
  implementation("com.fasterxml.jackson.module:jackson-module-jaxb-annotations:2.13.4")
  implementation("javax.jms:javax.jms-api:2.0.1")
  implementation("org.assertj:assertj-core:3.23.1")
  implementation("org.apache.commons:commons-lang3:3.12.0")

  implementation("org.springframework:spring-web")

  testImplementation("org.springframework.boot:spring-boot-starter-test")

}
repositories {
  mavenCentral()
}
