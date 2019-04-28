package ru.proj.reactfront

import kotlinext.js.*
import react.dom.*
import ru.proj.reactfront.app.app
import kotlin.browser.*

fun main(args: Array<String>) {
    window.onload = {
        requireAll(require.context("src", true, js("/\\.css$/")))

        render(document.getElementById("root")) {
            app()
        }
    }
}
