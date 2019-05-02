package ru.proj.reactfront.app

import react.*
import react.dom.*
import ru.proj.reactfront.logo.*
import ru.proj.reactfront.ticker.*
import sample.*

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        div("App-header") {
            logo()
            h2 {
                +"Welcome to React with Kotlin"
            }
        }
        h3 {
            val sample = Sample()
            +"Platform: ${Platform.name} with number ${sample.checkMe()}"
        }
        p("App-intro") {
            +"To get started, edit "
            code { +"app/App.kt" }
            +" and save to reload."
        }
        p("App-ticker") {
            ticker()
        }
    }
}

fun RBuilder.app() = child(App::class) {}
