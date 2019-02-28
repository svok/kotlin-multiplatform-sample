import com.bmuschko.gradle.docker.DockerRemoteApiPlugin
import com.bmuschko.gradle.docker.tasks.DockerVersion
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import com.moowork.gradle.node.yarn.YarnTask

val kotlin_version: String by project

val node_version: String by project
val yarn_version: String by project
val node_plugin_version: String by project
val docker_plugin_version: String by project

plugins {
    id("kotlin-platform-js")
    id("com.moowork.node")
    id("com.bmuschko.docker-remote-api")
}

repositories {
    jcenter()
    mavenCentral()
}

//val distDir = project.buildDir.resolve("dist")
val distDir = project.projectDir.resolve("dist")
val assetsDir = project.projectDir.resolve("assets")
val srcDir = project.projectDir.resolve("src")

node {
    version = node_version
    yarnVersion = yarn_version
    download = true
    download = true
    workDir = file("${project.buildDir}/nodejs")
    yarnWorkDir = file("${project.buildDir}/yarn")
    nodeModulesDir = file("${project.projectDir}")
}

tasks {

    withType<Jar> {
        dependsOn("ngBuild")
        archiveBaseName.set(project.name)
    }


    task<YarnTask>("ngBuild") {
        dependsOn("yarn_install")

        inputs.files(fileTree("node_modules"))
        inputs.files(fileTree("src"))
        inputs.file("angular.json")
        inputs.file("package.json")
//        inputs.file('webpack.config.js')

        outputs.dir("dist")

        args = listOf("run", "build")
    }

    val webdriverUpdate = register("webdriverUpdate", YarnTask::class) {
        args = listOf("run", "update-driver")
    }

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

//tasks.getByName("processResources").dependsOn("ngBuild")
//    register("build") {
//        dependsOn("ngBuild")
//    }

//    register("check") {
//        dependsOn("ngTest")
//    }

//    register("test") {
//        dependsOn("ngTest")
//    }

//    register("clean") {
//        delete("node_modules")
//        delete("bower_components")
//        delete("coverage")
//        delete("documentation")
//        delete("dist")
//        delete("src/app/generated")
//    }

    task<DockerVersion>(name = "dockerVersion") {
        group = DockerRemoteApiPlugin.DEFAULT_TASK_GROUP
    }

    val createDockerfileTask = task<Dockerfile>("dockerFile") {
        group = DockerRemoteApiPlugin.DEFAULT_TASK_GROUP
        dependsOn("ngBuild")

        description = "Creates the Docker image for the Angular application."
        from("registry.proj.ru/nginx:current")
        label(
            mutableMapOf(
                "MAINTAINER" to """Sergey Okatov "sokatov@gmail.com""""
            )
        )
        copyFile("call", "/var/www/site")
        copyFile("nginx.conf", "/etc/nginx/sites-available/default")

//  entryPoint("java")
//  defaultCommand( "-jar", "/app/${bootJar.archiveName}")
//  exposePort(8081)

//    runCommand("apk --update --no-cache add curl")
//    instruction("HEALTHCHECK CMD curl -f http://localhost:8080/health || exit 1")
    }

    task<Sync>("syncArchive") {
        group = DockerRemoteApiPlugin.DEFAULT_TASK_GROUP
        dependsOn("ngBuild", "dockerFile")

        group = DockerRemoteApiPlugin.DEFAULT_TASK_GROUP
        description = "Copies the distribution resources to a temporary directory for image creation."
        from("dist", "nginx.conf")
        preserve {
            include("Dockerfile")
        }
        into(createDockerfileTask.destFile.asFile.get().parentFile)
    }

}

dependencies {
    compile(project(":proj-common"))
}
