pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            name = "Kotlin EAP (for kotlin-frontend-plugin)"
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "kotlin-multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "kotlinx-serialization" -> "org.jetbrains.kotlin:kotlin-serialization:${requested.version}"
                "org.jetbrains.kotlin.frontend" -> useModule("org.jetbrains.kotlin:kotlin-frontend-plugin:${requested.version}")
//                "com.android.application" -> useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
}

rootProject.name = "proj-root"
rootProject.buildFileName = "build.gradle.kts"
//enableFeaturePreview("GRADLE_METADATA")

include("proj-common")
include("proj-backend")
include("proj-angularfront")
include("proj-androidfront")

fun configureGradleScriptKotlinOn(project: ProjectDescriptor) {
    project.buildFileName = "build.gradle.kts"
    project.children.forEach { configureGradleScriptKotlinOn(it) }
}

configureGradleScriptKotlinOn(rootProject)
