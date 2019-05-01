import org.jetbrains.kotlin.gradle.frontend.KotlinFrontendExtension
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackExtension

plugins {
    id("kotlin-platform-js")
//    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.frontend")
}

repositories {
    mavenCentral()
    maven { setUrl("http://dl.bintray.com/kotlin/kotlinx.html") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    maven { setUrl("https://kotlin.bintray.com/kotlin-js-wrappers") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlinx") }

}

tasks {
    compileKotlin2Js {
        kotlinOptions {
            metaInfo = true
//            outputFile = "${project.buildDir.path}/js/${project.name}.js"
            outputFile = "${project.buildDir.path}/src/index.js"
            sourceMap = true
            sourceMapEmbedSources = "always"
            moduleKind = "commonjs"
            main = "call"
        }
    }

    compileTestKotlin2Js {
        kotlinOptions {
            metaInfo = true
            outputFile = "${project.buildDir.path}/js-tests/${project.name}-tests.js"
            sourceMap = true
            moduleKind = "commonjs"
            main = "call"
        }
    }

    val copyFiles = register("copyFiles", Sync::class) {
        copy {
            preserve {
                include("**")
            }
            from(sourceSets.main.get().allSource)
            exclude("**/*.kt")
            into("$buildDir/src")
        }

        copy {
            preserve {
                include("**")
            }
            from("src/main/web") {
                exclude("index.html")
            }
            into("$buildDir/src")
        }
    }

    assemble.get().dependsOn(copyFiles)
//    processResources.get().dependsOn(copyFiles)
}

kotlinFrontend {
    npm {
        dependency("style-loader") // production dependency
        dependency("react")
        dependency("react-dom")
        dependency("react-router-dom")
        dependency("react-app-polyfill")
        dependency("require-context")

        devDependency("react-dev-utils", "^9.0.0")
        devDependency("extract-text-webpack-plugin", "next")
        dependency("sass-loader")
        dependency("node-sass")
        devDependency("webpack", "4.29.6")
        devDependency("webpack-dev-server", "3.2.1")

        devDependency("karma")
        devDependency("react-scripts-kotlin")
        devDependency("react-scripts", "latest")
    }
    webpackBundle {
        bundleName = "main"
        sourceMapEnabled = false   // enable/disable source maps
        mode = "development"
        contentPath = file("src/main/web")
        publicPath = "/"  // web prefix
        host = "localhost" // dev server host
        port = 8088   // dev server port
        stats = "verbose" // "errors-only", "minimal", "none", "normal", "verbose"
    }
    define("PRODUCTION", false)
}

dependencies {
    implementation(project(":proj-common"))
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains:kotlin-react:16.6.0-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-react-redux:5.0.7-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-redux:4.0.0-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-react-dom:16.6.0-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-css-js:1.0.0-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-extensions:1.0.1-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-react-router-dom:4.3.1-pre.70-kotlin-1.3.21")

    testImplementation(kotlin("test-js"))
    testImplementation("org.jetbrains:kotlin-mocha:3.0.1-pre.70-kotlin-1.3.21")
}

fun kotlinFrontend(block: KotlinFrontendExtension.() -> Unit) {
    val kotlinFrontend: KotlinFrontendExtension by project
    kotlinFrontend.apply(block)
}
fun KotlinFrontendExtension.webpackBundle(block: WebPackExtension.() -> Unit) {
    bundle<WebPackExtension>("webpack") { this as WebPackExtension; block() }
}
