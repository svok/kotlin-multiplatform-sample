buildscript {
    val kotlin_version: String by project
    val kotlin_frontend_version: String by project
    val android_plugin_version: String by project

    repositories {
        jcenter()
        google()
        mavenCentral()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath(kotlin("serialization", version = kotlin_version))
        classpath(kotlin("frontend-plugin",  version = kotlin_frontend_version))
        classpath("com.android.tools.build:gradle:$android_plugin_version")
    }
}

plugins {
    val kotlin_version: String by project
    val node_plugin_version: String by project
    val docker_plugin_version: String by project
    val android_plugin_version: String by project

    kotlin("multiplatform") version kotlin_version apply false
    id("maven-publish")
    id("com.moowork.node") version node_plugin_version
    id("com.bmuschko.docker-remote-api") version docker_plugin_version apply false
//    id("com.android.application") version android_plugin_version apply false
//    id("kotlin-android") version kotlin_version apply false


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
