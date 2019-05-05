import groovy.json.JsonBuilder
import org.gradle.api.tasks.TaskAction

class KotlinJar2NpmPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project {

            project {
                plugins {
                    id("com.moowork.node") version "1.3.1"
                }
            }

            tasks {
                register("myCopyTask", Copy::class) {
                    group = "sample"
                    from("build.gradle.kts")
                    into("build/copy")
                }
            }
        }
    }

    @TaskAction
    fun run() {
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
                nmReal.resolve(name).ensureSymlink(outDir)
            }
        }
    }

}
