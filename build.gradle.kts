// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath(libs.compose.compiler.gradle.plugin)
        classpath (libs.kotlin.gradle.plugin)


    }
}

apply(plugin = "org.jetbrains.kotlin.plugin.compose")