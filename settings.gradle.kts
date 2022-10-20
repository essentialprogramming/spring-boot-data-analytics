pluginManagement {
    includeBuild("build-conventions")
    includeBuild("build-settings")
}

plugins {
    id("repository-conventions")
}

rootProject.name = "spring-boot-data-analytics"

includeBuild("platform")

include("essentialprogramming-window-functions")
include("essentialprogramming-search")
include("essentialprogramming-util")
include("essentialprogramming-base")
