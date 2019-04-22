plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

val kotlin_version: String by project

android {
    compileSdkVersion(28)
    buildToolsVersion = "28.0.3"
    defaultConfig {
        applicationId = "com.example.androidfront"
        minSdkVersion(24)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
//        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    testBuildType = "debug"
    sourceSets {
        get("main").java.srcDirs("src/main/kotlin")
        get("androidTest").java.srcDirs("src/androidTest/kotlin")
        get("test").java.srcDirs("src/test/kotlin")
    }
    lintOptions {
        warning( "InvalidPackage")
    }
}

dependencies {
    implementation(project(":proj-common"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8", kotlin_version))
    implementation(kotlin("test-junit", kotlin_version))
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    implementation("com.android.support:design:28.0.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test:rules:1.1.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.0")
}

repositories {
    mavenCentral()
    maven("http://repository.jetbrains.com/all")
}
