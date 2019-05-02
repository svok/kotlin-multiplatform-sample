import com.moowork.gradle.node.yarn.YarnTask
import groovy.json.JsonBuilder
import java.io.IOException
import java.nio.file.Files

plugins {
    id("kotlin-platform-js")
    id("com.moowork.node")
//    id("kotlinx-serialization")
//    id("org.jetbrains.kotlin.frontend")
}

repositories {
    mavenCentral()
    maven { setUrl("http://dl.bintray.com/kotlin/kotlinx.html") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap") }
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

//    val webdriverUpdate = register("webdriverUpdate", YarnTask::class) {
//        args = listOf("run", "update-driver")
//    }

    task<YarnTask>("ngTest") {
        dependsOn("yarn_install")
        dependsOn("webdriverUpdate")
        args = listOf("run", "testPhantom")
    }

    task<YarnTask>("updateDeps") {
        args = listOf("run", "update")
    }

    task<YarnTask>("serve") {
        args = listOf("run", "start")
    }

    clean.get().doLast {
        println("Delete dist and node_modules")
        file("$projectDir/dist").deleteRecursively()
        file("$projectDir/node_modules").deleteRecursively()
    }

    val jar2npm = register("jar2npm") {

        dependsOn("yarn_install")
        val conf = configurations.testRuntimeClasspath.get()

        val nmReal = project.projectDir.resolve("node_modules").mkDirOrFail()
        val nmImported = project.buildDir.resolve("node_modules_imported").mkDirOrFail()

        val allJars = conf
            .resolvedConfiguration
            .resolvedArtifacts

        val names = allJars
            .filter { it.file.isFile && it.file.exists() }
            .distinctBy { it.file.canonicalFile.absolutePath }
            .map {
                val version = it.moduleVersion.id.version
                val file = it.file

                val metaName =
                    zipTree(file).find { fName -> fName.name.endsWith(".meta.js") }?.name ?: return@map ""
                val name = metaName.replace("\\.meta\\.js\$".toRegex(), "")
                val js = "$name.js"

                val outDir = nmImported.resolve(name).mkDirOrFail()
                copy {
                    from(zipTree(file))
                    into(outDir)
                }
                val packageJson = mapOf(
                    "name" to name,
                    "version" to version,
                    "main" to js,
                    "_source" to "gradle"
                )

                outDir.resolve("package.json").bufferedWriter().use { out ->
                    out.appendln(JsonBuilder(packageJson).toPrettyString())
                }
                name
            }
            .filter { it.isNotBlank() }


        doLast {
            names.forEach { name ->
                    val outDir = nmImported.resolve(name).mkDirOrFail()
//                    println("Ensure symlink ${name} -> ${outDir.absoluteFile}")
                    nmReal.resolve(name).ensureSymlink(outDir)
                }
        }
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

fun File.mkDirOrFail(): File {
    if (!mkdirs() && !exists()) {
        throw IOException("Failed to create directories at $this")
    }
    return this
}

fun File.ensureSymlink(file: File) {
    if (this.exists() && Files.isSymbolicLink(toPath())) return
    Files.createSymbolicLink(toPath(), file.toPath())
}
