package sample

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializerTest {

    @Test
    fun serializerTest() {
        val json = Json.stringify(SomeData.serializer(), SomeData(17, "my test"))
        assertEquals("""{"x":17,"s":"my test"}""", json)
    }

    @Test
    fun sertializedDataTest() {
        assertEquals("""{"x":24,"s":"my string"}""", dataJson)
    }
}
