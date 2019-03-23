package sample

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SomeData(
    var x: Int = 0,
    var s: String = ""
)

val dataJson = Json.stringify(SomeData.serializer(), SomeData(24, "my string"))
