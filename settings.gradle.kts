/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: settings.gradle.kts
☆ Date: 2025-12-13
☆
*/
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // ☆ Description: Declare Kotlin plugins and their version to be resolved by Gradle.
        id("org.jetbrains.kotlin.android") version "1.9.0" apply false
        id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" apply false
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Towns.IO"
include(":app")