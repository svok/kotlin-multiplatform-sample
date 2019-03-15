buildscript {
    val kotlin_version: String by project
    val logback_version: String by project
    val node_plugin_version: String by project
    val docker_plugin_version: String by project

    repositories {
        jcenter()
        mavenCentral()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-frontend-plugin:0.0.45")
    }
}

plugins {
    val kotlin_version: String by project
    val node_plugin_version: String by project
    val docker_plugin_version: String by project
    val shadowjar_version: String by project

    kotlin("multiplatform") version kotlin_version apply false
    id("maven-publish")
    id("com.moowork.node") version node_plugin_version
    id("com.bmuschko.docker-remote-api") version docker_plugin_version apply false
    id("com.github.johnrengelman.shadow") version shadowjar_version apply false
}

repositories {
    mavenCentral()
}

val project_group: String by project
val project_version: String by project
group = project_group
version = project_version

subprojects {
    group = project_group
    version = project_version

    repositories {
        jcenter()
        mavenCentral()
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    }
}
