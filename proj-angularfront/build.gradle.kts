import com.moowork.gradle.node.yarn.YarnTask

plugins {
    id("kotlin-platform-js")
    id("com.moowork.node")
    id("com.bmuschko.docker-remote-api")
    id("com.crowdproj.plugins.jar2npm")
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
        inputs.file("angular.json")
        inputs.file("package.json")

        outputs.dir("dist")

        args = listOf("run", "build")
    }

    task<YarnTask>("ngUpgrade") {
        dependsOn("yarn_install")
        args = listOf("upgrade")
    }

    val webdriverUpdate = register("webdriverUpdate", YarnTask::class) {
        args = listOf("run", "update-driver")
    }

    task<YarnTask>("ngTest") {
        dependsOn("yarn_install")
        dependsOn("webdriverUpdate")
        args = listOf("run", "testPhantom")
    }

    task<YarnTask>("serve") {
        args = listOf("run", "start")
    }

//    task<DockerVersion>("dockerVersion") {
//        group = DockerRemoteApiPlugin.DEFAULT_TASK_GROUP
//    }
//
//    val createDockerfileTask = task<Dockerfile>("dockerFile") {
//        group = DockerRemoteApiPlugin.DEFAULT_TASK_GROUP
//        dependsOn("ngBuild")
//
//        description = "Creates the Docker image for the Angular application."
//        from("registry.proj.ru/nginx:current")
//        label(
//            mutableMapOf(
//                "MAINTAINER" to """Sergey Okatov "sokatov@gmail.com""""
//            )
//        )
//        copyFile("call", "/var/www/site")
//        copyFile("nginx.conf", "/etc/nginx/sites-available/default")
//
//    }
//
//    task<Sync>("syncArchive") {
//        group = DockerRemoteApiPlugin.DEFAULT_TASK_GROUP
//        dependsOn("ngBuild", "dockerFile")
//
//        group = DockerRemoteApiPlugin.DEFAULT_TASK_GROUP
//        description = "Copies the distribution resources to a temporary directory for image creation."
//        from("dist", "nginx.conf")
//        preserve {
//            include("Dockerfile")
//        }
//        into(createDockerfileTask.destFile.asFile.get().parentFile)
//    }

    clean.get().doLast {
        println("Delete dist and node_modules")
        file("$projectDir/dist").deleteRecursively()
        file("$projectDir/node_modules").deleteRecursively()
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

    ngBuild.dependsOn(jar2npm)
}

dependencies {
    implementation(project(":proj-common"))
}
