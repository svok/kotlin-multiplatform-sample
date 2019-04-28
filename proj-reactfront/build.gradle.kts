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
            outputFile = "${project.buildDir.path}/js/${project.name}.js"
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
}

kotlinFrontend {
    npm {
        dependency("style-loader") // production dependency
        dependency("react")
        dependency("react-dom")
        dependency("react-router-dom")
        dependency("react-app-polyfill")
        devDependency("karma")
//        devDependency("babel-loader")
//        devDependency("@babel/core")
//        devDependency("@babel/preset-env")
//        devDependency("react-scripts-kotlin")
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
}

dependencies {
    implementation(project(":proj-common"))
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains:kotlin-react:16.6.0-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-react-redux:5.0.7-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-redux:4.0.0-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-react-dom:16.6.0-pre.70-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-css-js:1.0.0-pre.70-kotlin-1.3.21")
//    implementation("org.jetbrains:kotlin-extensions:1.0.1-pre.70-kotlin-1.3.21")
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
//fun KotlinFrontendExtension.npm(block: NpmExtension.() -> Unit) = configure<NpmExtension>(block)
