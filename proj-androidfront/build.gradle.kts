plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

val kotlin_version: String by project

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.example.androidfront"
        minSdkVersion(24)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    testBuildType = "debug"
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test", "androidTest")

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8", kotlin_version))
    implementation(kotlin("test-junit", kotlin_version))
    implementation("com.android.support:appcompat-v7:28.0.0")
    testImplementation("junit:junit:4.12")
    implementation("com.android.support.test:runner:1.0.2")
    implementation("com.android.support.test:rules:1.0.2")
    implementation("com.android.support.constraint:constraint-layout:1.1.3")
    implementation("com.android.support:design:28.0.0")
//    androidTestImplementation("com.android.support.test:runner:1.0.2")
//    androidTestImplementation("com.android.support.test:rules:1.0.2")
//    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}

repositories {
    mavenCentral()
    maven("http://repository.jetbrains.com/all")
}
