# Sample Kotlin Ktor-Angular project

This progect is aimed to demonstrate the usage of the Kotlin multiplatform capabilities to build a
reusable code for both Angular and Ktor.

The reusable code is depsed in proj-common submodule. It has a description for JVM (jvmMain) and
JS (jsMain) archtectures, while the logics which is common for all architectures are placed in commonMain.

It us very convenient to place in commonMain interfaces between Angular and Ktor as well as formatting functions.