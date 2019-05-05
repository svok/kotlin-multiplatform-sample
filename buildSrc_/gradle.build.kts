plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    jcenter()
}

pluginBundle {
    // please change these URLs to point to your own website/repository
    website = "https://github.com/svok/kotlin-jar2npm-plugin"
    vcsUrl = "https://github.com/svok/kotlin-jar2npm-plugin.git"
    tags = listOf("jar", "npm", "node", "nodejs", "kotlin", "javascript", "react", "angular")
}

gradlePlugin {
    plugins {
        create("jar2npm") {
            id = "com.example.jar2npm"
            displayName = "Jar2Npm"
            description = "Plugin to extract Kotlin JAR JS packages to node_modules folder"
            implementationClass = "com.example.jar2npm.KotlinJar2NpmPlugin"
        }
    }
}
