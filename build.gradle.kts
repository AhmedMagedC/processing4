group = "org.processing"
version = "4.4.0"

plugins {
    kotlin("jvm") version libs.versions.kotlin apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrainsCompose) apply false

}