pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
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

fun configureGradleScriptKotlinOn(project: ProjectDescriptor) {
    project.buildFileName = "build.gradle.kts"
    project.children.forEach { configureGradleScriptKotlinOn(it) }
}

configureGradleScriptKotlinOn(rootProject)
