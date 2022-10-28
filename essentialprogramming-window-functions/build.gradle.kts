import org.jooq.meta.jaxb.Property
import java.nio.file.Path
import java.nio.file.Paths

plugins {
    id("java")
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("java-conventions")
    id("java-library")
    id("nu.studer.jooq") version "5.2.1"
}

val jooqVersion = "3.16.0"

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

    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.jooq:jooq-meta-extensions-liquibase:${jooqVersion}")

    jooqGenerator(project(":essentialprogramming-base"))
    jooqGenerator("org.jooq:jooq-meta-extensions-liquibase")
    jooqGenerator("org.liquibase:liquibase-core:3.10.3")
    jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
}

var baseProjectPath: String =  Paths.get(projectDir.absolutePath, "../essentialprogramming-base/").toString()

if (project(":essentialprogramming-base").hasProperty("projectDir")) {
    println("Found base project dir property")
    baseProjectPath = project(":essentialprogramming-base").property("projectDir").toString()
}

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
                                        .withValue("false")
                        )
                    }
                }
            }
        }
    }
}