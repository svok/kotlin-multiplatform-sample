# Sample multiplatform and multi-frontend Kotlin project

This progect is aimed to test the usage of the Kotlin multiplatform capabilities to build a
reusable code for as for differenct frontends as for backedn.

The reusable code is deposed in proj-common submodule. It has a description for JVM (jvmMain) and
JS (jsMain) archtectures, while the logics which is common for all architectures are placed in commonMain.

It us very convenient to place in commonMain interfaces between frontends and backends as well as formatting functions.

## Backend

As a backend framework we use KTOR

## Frontend

As a frontend we have tested the following frameworks:
- Android (JVM platform). Relized fully in **Kotlin**.
- React (JS platform). Realized fully in **Kotlin**.
- Angular (JS platform). Realized in mixture of Typescript and 
Kotlin and contains some issues. So it requires a workaround.

## Build system

Gradle 5.4 Kotlin DSL is used as a build system. For the JS platform frameworks we have used
[Moorwork Node Gradle plugin](https://plugins.gradle.org/plugin/com.moowork.node).

Other promising plugin is [Jetbrains Kotlin Frontend Plugin](https://github.com/Kotlin/kotlin-frontend-plugin).
But it looks abandoned: there are no updates for couple monthes, several merge requests are waiting for acception for 
monthes, there are plenty unclosed issues and annoying bugs.

The experiments in this project motivate me to plan another gradle plugin for kotlin-based 
JS frameworks.

## Installation

Just clone this project to any folder and build it with Gradle:

```bash
gradlew build
```

## Frontends peculiarities

### React

React frontend is based on official `react-cli` and `react-scripts`. To use these packages
usual `package.json` file is utilized. Kotlin JAR files are evidently not included into that.
They are separately attached to `node_modules` by the gradle script.

After `gradlew build` the comipled build is placed into `rootProject/proj-reactfront/dist` folder.
It is ready for deployment in production mode. If you need some other modes you can prepare it yourself
with standard `npm run command` (for example, `npm start`) in the folder `rootProject/proj-reactfront`.
