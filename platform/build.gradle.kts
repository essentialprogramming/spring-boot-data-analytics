plugins {
    `java-platform`
}

group = "com.essentialprogramming.platform"

object VersionGroup {
  const val mapstruct = "1.4.2.Final"
}

dependencies {
    constraints {

        // common app dependencies
        api("org.springdoc:springdoc-openapi-ui:1.6.6")
        api("org.mapstruct:mapstruct-processor:${VersionGroup.mapstruct}")
        api("org.mapstruct:mapstruct:${VersionGroup.mapstruct}")
        api("org.liquibase:liquibase-core:4.17.2")
        runtime("com.sun.xml.bind:jaxb-impl:2.3.6")

    }
}
