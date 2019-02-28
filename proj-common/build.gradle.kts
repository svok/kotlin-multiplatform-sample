plugins {
    kotlin("multiplatform")
    id("maven-publish")
}
repositories {
    mavenCentral()
}
//group="com.example"
//version="0.0.1"

val output_dir = "${rootProject.buildDir}/javascript-compiled"

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
                moduleKind = "commonjs"
                outputFile = "$output_dir/proj-common.js"
            }
        }
    }
    // For ARM, should be changed to iosArm32 or iosArm64
    // For Linux, should be changed to e.g. linuxX64
    // For MacOS, should be changed to e.g. macosX64
    // For Windows, should be changed to e.g. mingwX64
    linuxX64("linux")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
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
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val linuxMain by getting {
        }
        val linuxTest by getting {
        }
    }
}
