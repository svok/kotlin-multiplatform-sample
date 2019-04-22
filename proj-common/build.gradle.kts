plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("kotlinx-serialization")
}

val serialization_version: String by project
val node_version: String by project
val coroutine_version: String by project
val npmTarget = "${rootProject.buildDir}/npm"
val npmJsFile = "${project.name}.js"
val npmDir = project.name
val jsOutputFile = "$npmTarget/$npmDir/$npmJsFile"

// workaround for https://youtrack.jetbrains.com/issue/KT-27170
configurations.create("compileClasspath")

kotlin {
    jvm() {
        val main by compilations.getting {

        }
    }
    js() {
        val main by compilations.getting {
            kotlinOptions {
                metaInfo = true
                sourceMap = true
                moduleKind = "umd"
                outputFile = jsOutputFile
            }
        }
    }
    // For ARM, should be changed to iosArm32 or iosArm64
    // For Linux, should be changed to e.g. linuxX64
    // For MacOS, should be changed to e.g. macosX64
    // For Windows, should be changed to e.g. mingwX64
    // linuxX64("linux")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutine_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serialization_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutine_version")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
//        val linuxMain by getting {
//        }
//        val linuxTest by getting {
//        }
    }
}

tasks {
    task<Sync>("assembleWeb") {
//        val dependencies = configurations.get("jsMainImplementation").map {
//            val file = it
//            val (tDir, tVer) = "^(.*)-([\\d.]+-\\w+|[\\d.]+)\\.jar$"
//                .toRegex()
//                .find(file.name)
//                ?.groupValues
//                ?.drop(1)
//                ?: listOf("", "")
//            var jsFile: File? = null
//            copy {
//                from(zipTree(file.absolutePath), {
//                    includeEmptyDirs = false
//                    include { fileTreeElement ->
//                        val path = fileTreeElement.path
//                        val res = (path.endsWith(".js") || path.endsWith(".map"))
//                                && (path.startsWith("META-INF/resources/") || !path.startsWith("META-INF/"))
//                        if (res && path.endsWith(".js") && ! path.endsWith(".meta.js")) jsFile = fileTreeElement.file
//                        res
//                    }
//                })
//                into("$npmTarget/$tDir")
//            }
//            jsFile?.also { packageJson(tDir, it, tVer) }
//            tDir to jsFile
//        }
//            .filter { it.second != null }
//            .map { it.first to it.second!! }
//            .toMap()
//
//        packageJson(npmDir, File(jsOutputFile), project.version.toString(), dependencies)
//        dependsOn("jsMainClasses")
    }

//    assemble.get().dependsOn("assembleWeb")
}

fun packageJson(dir: String, jsFile: File, version: String, dependencies: Map<String, File> = emptyMap()) {
    val deps = dependencies.map {
        println("js2name: ${it.value.name} -> ${js2Name(it.value)}")
        """"${js2Name(it.value)}": "file:../${it.key}""""
    }.joinToString(",\n            ")
    println("DEPS: ${deps}")
    val text = """
        {
          "name": "${js2Name(jsFile)}",
          "version": "${version}",
          "main": "./${jsFile.name}",
          "dependencies": {
            ${deps}
          }
        }
    """.trimIndent()
    File("$npmTarget/$dir/package.json").apply {
        if (parentFile.exists()) {
            parentFile.delete()
        }
        parentFile.mkdirs()
        writeText(text)
    }
}

fun js2Name(jsFile: File) = jsFile.name.replace("""\.js$""".toRegex(), "")
