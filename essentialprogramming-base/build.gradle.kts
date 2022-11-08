import org.jooq.meta.jaxb.Property
import java.nio.file.Path
import java.nio.file.Paths

plugins {
    id("java")
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("java-conventions")
    id("maven-publish")
    id("java-library")
    id("nu.studer.jooq") version "7.1.1"
}

val jooqVersion = "3.16.0"

buildscript {
    configurations["classpath"].resolutionStrategy.eachDependency {
        if (requested.group == "org.jooq") {
            useVersion("3.16.0")
        }
    }
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
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.4")
    implementation("org.assertj:assertj-core:3.23.1")
    implementation("org.mapstruct:mapstruct")

//  implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.jooq:jooq:${jooqVersion}")
    implementation("org.jooq:jooq-meta-extensions-liquibase:${jooqVersion}")

    jooqGenerator("org.postgresql:postgresql")
    jooqGenerator("org.jooq:jooq-meta-extensions-liquibase:${jooqVersion}")
    jooqGenerator("org.liquibase:liquibase-core:3.10.3")
    jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    jooqGenerator("org.yaml:snakeyaml:1.33")


    testRuntimeOnly("com.h2database:h2:2.1.210")

    testImplementation("org.jooq:jooq:${jooqVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}


var baseProjectPath: String = projectDir.toString()
val rootPath: Path = Paths.get(baseProjectPath, "src/main/resources")
val changelogPath: Path = Paths.get(rootPath.toString(), "db/changelog/db.changelog-master.xml")

jooq {
    version.set(jooqVersion)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)

            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN

                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    target.apply {
                        packageName = "com.base.persistence.entities.generated"
                    }
                    database.apply {
                        name = "org.jooq.meta.extensions.liquibase.LiquibaseDatabase"
                        properties = listOf(
                                Property()
                                        .withKey("rootPath")
                                        .withValue(rootPath.toString()),
                                Property()
                                        .withKey("scripts")
                                        .withValue(changelogPath.toString()),
                                Property()
                                        .withKey("includeLiquibaseTables")
                                        .withValue(false.toString())
                        )
                    }

                    generate.apply {
                        isRelations = true
                        isDeprecated = false
                        isRecords = true
                        isPojos = true
                        isPojosEqualsAndHashCode = true
                        isImmutablePojos = false
                        isFluentSetters = false
                        isJavaTimeTypes = true
                    }
                }
            }
        }
    }
}