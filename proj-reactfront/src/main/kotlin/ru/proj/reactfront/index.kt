package ru.proj.reactfront

import kotlinext.js.*
import react.dom.*
import ru.proj.reactfront.app.app
import kotlin.browser.*
import kotlin.js.RegExp

fun main(args: Array<String>) {
    window.onload = {
//        requireAll(require.context("src", true, js("/\\.css$/") as RegExp))

        render(document.getElementById("root")) {
            app()
        }
    }
}
