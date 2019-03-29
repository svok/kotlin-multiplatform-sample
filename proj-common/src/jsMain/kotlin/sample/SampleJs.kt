package sample

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual class Sample {
    actual fun checkMe() = 12
}

actual object Platform {
    actual val name: String = "JS"
}

@JsName("delayedAsync")
fun Tester.delayedAsync() = GlobalScope.promise { delayed() }
