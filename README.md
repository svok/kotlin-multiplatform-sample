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
- Angular (JS platform). Realized in mixture of **Typescript** and 
**Kotlin**.

## Build system

Gradle 5.4 Kotlin DSL is used as a build system. For the JS platform frameworks we have used
[Moowork Node Gradle plugin](https://plugins.gradle.org/plugin/com.moowork.node).

Other promising plugin is [Jetbrains Kotlin Frontend Plugin](https://github.com/Kotlin/kotlin-frontend-plugin).
But it looks abandoned: there are no updates for couple monthes, several merge requests are waiting for acception for 
monthes, there are plenty unclosed issues and annoying bugs.

The experiments in this project motivate us to publish another gradle plugin for kotlin-based 
JS frameworks: [kotlin-jar2npm-plugin](https://github.com/svok/kotlin-jar2npm-plugin).
This plugin extracts the content of the KotlinJS JAR-packages to node_modules package repository
and allows KotlinJS code available in all other JavaScript projects.

## Installation

Just clone this project to any folder and build it with Gradle:

```bash
git clone https://github.com/svok/kotlin-multiplatform-sample.git

cd kotlin-multiplatform-sample

./gradlew build
```

## Frontends peculiarities

### React

React frontend is based on official `react-cli` and `react-scripts`. To use these packages
usual `package.json` file is utilized. Kotlin JAR files are evidently not included into that.
They are separately attached to `node_modules` by the gradle script.

After `gradlew build` the comipled build is placed into `rootProject/proj-reactfront/dist` folder.
It is ready for deployment in production mode. If you need some other modes you can prepare it yourself
with standard `npm run command` (for example, `npm start`) in the folder `rootProject/proj-reactfront`.

### Angular

This is a standard angular-cli project written on Typescript. The external Kotlin library is attached with
the [kotlin-jar2npm-plugin](https://github.com/svok/kotlin-jar2npm-plugin) as well. To work with it, Kotlin
library must be precompiled with the jar2npm task:
```bash
./gradlew :proj-angularfront:jar2npm
```
After that your IDE (Intelleij Idea in my case) should see that:
```typescript
import {sample} from 'proj-common';
```

## Contribution

You are welcome to contibute this project. If you want to add some other framework or make other improvements
just clone the project and depose your pull request.

For any difficulties and bug reports please use the [github issues](https://github.com/svok/kotlin-multiplatform-sample/issues)
section.
