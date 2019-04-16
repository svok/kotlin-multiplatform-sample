buildscript {
    val kotlin_version: String by project
    val kotlin_frontend_version: String by project

    repositories {
        jcenter()
        google()
        mavenCentral()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-frontend-plugin:$kotlin_frontend_version")
        classpath("com.android.tools.build:gradle:3.3.2")
    }
}

plugins {
    val kotlin_version: String by project
    val node_plugin_version: String by project
    val docker_plugin_version: String by project
    val shadowjar_version: String by project

    kotlin("multiplatform") version kotlin_version apply false
//    id("org.jetbrains.kotlin.android") version kotlin_version apply false
//    id("com.android.application")
//    id("kotlin-android") version kotlin_version apply false
//    id("kotlin-android-extensions") version kotlin_version apply false
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

project(":proj-androidfront") {
    repositories {
        google()
        jcenter()
    }
}