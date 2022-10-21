import org.jooq.meta.jaxb.Property
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Configuration

plugins {
  id("java")
  id("org.springframework.boot") version "2.7.4"
  id("io.spring.dependency-management") version "1.0.14.RELEASE"
  id("java-conventions")
  id("java-library")
}

buildscript {
  dependencies {
    classpath("org.jooq:jooq-codegen:3.17.4")
    classpath("org.jooq:jooq-meta-extensions-hibernate:3.17.4")
    classpath("org.jooq:jooq-meta-extensions")
  }
}

tasks.bootJar { enabled = true }
tasks.jar { enabled = false }

springBoot {
  buildInfo()
}

val jooqConfiguration: Configuration = Configuration()
        .withGenerator(Generator()
                .withDatabase(Database()
                        .withName("org.jooq.meta.extensions.jpa.JPADatabase")
                        .withProperties(
                                Property()
                                        .withKey("packages")
                                        .withValue("com.base.persistence.entities"),
                                Property()
                                        .withKey("useAttributeConverters")
                                        .withValue("true"),
                                Property()
                                        .withKey("unqualifiedSchema")
                                        .withValue("none")
                        ))
                .withTarget(org.jooq.meta.jaxb.Target()
                        .withPackageName("com.base.persistence.entities.generated")
                        .withDirectory("src/main/java"))
                .withGenerate( Generate()
                        .withPojos(true)
                        .withDaos(true)))


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

  GenerationTool.generate(jooqConfiguration)
}