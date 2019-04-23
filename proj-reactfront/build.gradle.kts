plugins {
    id("kotlin-platform-js")
//    id("kotlinx-serialization")
}

repositories {
    mavenCentral()
    maven { setUrl("http://dl.bintray.com/kotlin/kotlinx.html") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    maven { setUrl("https://kotlin.bintray.com/kotlin-js-wrappers") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlinx") }

}

dependencies {
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
    testImplementation(kotlin("mocha", "3.0.1-pre.70-kotlin-1.3.21"))
}
