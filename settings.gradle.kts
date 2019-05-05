pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { setUrl("https://dl.bintray.com/svok/jar2npm") }
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "kotlin-multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "kotlinx-serialization" -> "org.jetbrains.kotlin:kotlin-serialization:${requested.version}"
                "org.jetbrains.kotlin.frontend" -> useModule("org.jetbrains.kotlin:kotlin-frontend-plugin:${requested.version}")
                "com.crowdproj.plugins.jar2npm" -> useModule("com.crowdproj.plugins:jar2npm-plugin:${requested.version}")
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
include("proj-reactfront")

fun configureGradleScriptKotlinOn(project: ProjectDescriptor) {
    project.buildFileName = "build.gradle.kts"
    project.children.forEach { configureGradleScriptKotlinOn(it) }
}

configureGradleScriptKotlinOn(rootProject)
