package ru.proj.reactfront.logo

import react.*
import react.dom.*

@JsModule("ru/proj/reactfront/logo/react.svg")
external val reactLogo: dynamic
@JsModule("ru/proj/reactfront/logo/kotlin.svg")
external val kotlinLogo: dynamic

fun RBuilder.logo(height: Int = 100) {
    div("Logo") {
        attrs.jsStyle.height = height
        img(alt = "React ru.proj.reactfront.logo.logo", src = reactLogo, classes = "Logo-react") {}
        img(alt = "Kotlin ru.proj.reactfront.logo.logo", src = kotlinLogo, classes = "Logo-kotlin") {}
    }
}
