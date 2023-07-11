package qixi.propio.test

import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.writeString
import qixi.propio.readKeyValue
import kotlin.test.Test
import kotlin.test.assertEquals

class DelimiterTest {

    @Test
    fun readKeyEqualsValue() {
        val source: Source = Buffer().also { it.writeString("key=value") }
        val (key, value) = source.readKeyValue()
        assertEquals(key, "key")
        assertEquals(value, "value")
    }

    @Test
    fun readKeyColonValue() {
        val source: Source = Buffer().also { it.writeString("key:value") }
        val (key, value) = source.readKeyValue()
        assertEquals(key, "key")
        assertEquals(value, "value")
    }

}