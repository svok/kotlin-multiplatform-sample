package sample

import kotlinx.coroutines.delay

expect class Sample() {
    fun checkMe(): Int
}

expect object Platform {
    val name: String
}

class Tester {
    suspend fun delayed() {
        delay(1000)
        println("Suspend function is run")
    }
}

fun hello(): String = "Hello from ${Platform.name}"
