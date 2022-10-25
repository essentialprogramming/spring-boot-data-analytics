import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property

buildscript {
  dependencies {
    // https://medium.com/@marekscholle/jooq-with-gradle-and-java-11-a6f10efff297
    classpath("org.glassfish.jaxb:jaxb-runtime:4.0.0")
    classpath("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    classpath("com.sun.xml.bind:jaxb-impl:4.0.0")
    classpath("jakarta.persistence:jakarta.persistence-api:3.1.0")

  }
}

plugins {
  id("java")
  id("org.springframework.boot") version "2.7.4"
  id("io.spring.dependency-management") version "1.0.14.RELEASE"
  id("java-conventions")
  id("java-library")
  id("nu.studer.jooq") version "8.0"
}

repositories {
  google()
  mavenCentral()
  maven {
    url = uri("https://repo1.maven.org/maven2/")
  }
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

  jooqGenerator(project(":essentialprogramming-base"))

  // This solves the JAXB dependency missing from Java 11+
  jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
  jooqGenerator("com.sun.xml.bind:jaxb-impl:4.0.0")
  jooqGenerator("org.glassfish.jaxb:jaxb-runtime:4.0.0")
  // https://github.com/etiennestuder/gradle-jooq-plugin/issues/34
  jooqGenerator("org.jooq:jooq-meta-extensions-hibernate:3.17.4")
  jooqGenerator("javax.xml.bind:jaxb-api:2.2.4")
  jooqGenerator("jakarta.persistence:jakarta.persistence-api:3.1.0")

}

jooq {
  version.set("3.17.4")
  edition.set(JooqEdition.OSS)

  configurations {
    create("main") {
      jooqConfiguration.apply {
        logging = Logging.WARN

        generator.apply {
          name = "org.jooq.codegen.DefaultGenerator"
          database.apply {
            name = "org.jooq.meta.extensions.jpa.JPADatabase"
            properties = listOf(
              Property().apply {
                key = "packages"
                value = "com.base.persistence.entities"
              },
              Property().apply {
                key = "useAttributeConverters"
                value = "true"
              }
            )
          }
          generate.apply {
            isDeprecated = false
            isRecords = false
            isImmutablePojos = false
            isFluentSetters = false
          }
          target.apply {
            packageName = "com.base.persistence.entities.generated"
            directory = "src/generated/jooq"
          }
          strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
        }
      }
    }
  }
}