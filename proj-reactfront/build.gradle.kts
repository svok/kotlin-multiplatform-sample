import com.moowork.gradle.node.yarn.YarnTask

plugins {
    id("kotlin-platform-js")
    id("com.moowork.node")
    id("com.crowdproj.plugins.jar2npm")
}

repositories {
    mavenCentral()
    maven { setUrl("http://dl.bintray.com/kotlin/kotlinx.html") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    maven { setUrl("https://kotlin.bintray.com/kotlin-js-wrappers") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlinx") }

}

node {
    download = true
    workDir = file("${project.buildDir}/node")
    npmWorkDir = file("${project.buildDir}/node")
    yarnWorkDir = file("${project.buildDir}/node")
    nodeModulesDir = file("${project.projectDir}")
}

tasks {

    withType<Jar> {
        dependsOn("ngBuild")
        archiveBaseName.set(project.name)
    }

    val ngBuild = task<YarnTask>("ngBuild") {
        dependsOn("yarn_install")

        inputs.files(fileTree("node_modules"))
        inputs.files(fileTree("src"))
        inputs.file("package.json")

        outputs.dir("dist")

        args = listOf("run", "build")
    }

    task<YarnTask>("ngUpgrade") {
        dependsOn(yarnSetup)
        args = listOf("upgrade")
    }

    task<YarnTask>("ngTest") {
        dependsOn("yarn_install")
        args = listOf("run", "testPhantom")
    }

    task<YarnTask>("serve") {
        args = listOf("run", "start")
    }

    clean.get().doLast {
        println("Delete dist and node_modules")
        file("$projectDir/dist").deleteRecursively()
        file("$projectDir/node_modules").deleteRecursively()
    }

    val copyFiles = register("copyFiles", Copy::class) {
        from("$buildDir/js")
        from(sourceSets.main.get().allSource)
        exclude("**/*.kt")
        into("$buildDir/src")
    }

    compileKotlin2Js {
        kotlinOptions {
            metaInfo = true
            outputFile = "${project.buildDir.path}/js/index.js"
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

    processResources.get().dependsOn(copyFiles)
    ngBuild.dependsOn(jar2npm)
}

val serialization_version: String by project

dependencies {
    implementation(project(":proj-common"))
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serialization_version")
    implementation("org.jetbrains:kotlin-react:16.9.0-pre.89-kotlin-1.3.60")
    implementation("org.jetbrains:kotlin-react-redux:5.0.7-pre.89-kotlin-1.3.60")
    implementation("org.jetbrains:kotlin-redux:4.0.0-pre.89-kotlin-1.3.60")
    implementation("org.jetbrains:kotlin-react-dom:16.9.0-pre.89-kotlin-1.3.60")
    implementation("org.jetbrains:kotlin-css-js:1.0.0-pre.78-kotlin-1.3.21")
    implementation("org.jetbrains:kotlin-extensions:1.0.1-pre.89-kotlin-1.3.60")
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.89-kotlin-1.3.60")
    implementation("org.jetbrains:kotlin-react-router-dom:4.3.1-pre.89-kotlin-1.3.60")

    testImplementation(kotlin("test-js"))
    testImplementation("org.jetbrains:kotlin-mocha:3.0.1-pre.89-kotlin-1.3.60")
}
