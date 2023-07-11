package qixi.propio.test

import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.writeString
import qixi.propio.MIN_KEY_VALUE_LENGTH
import qixi.propio.readKeyValue
import kotlin.test.Test
import kotlin.test.assertEquals

class KeyValueTest {

    @Test
    fun readKeyValueSmall() {
        val source: Source = Buffer().also { it.writeString("=v") }
        val (key, value) = source.readKeyValue()
        assertEquals(key, null)
        assertEquals(value, null)
    }

    @Test
    fun readKeyEmpty() {
        val source: Source = Buffer().also { it.writeString("...") }
        val (key, value) = source.readKeyValue()
        assertEquals(key, null)
        assertEquals(value, "...")
    }

    @Test
    fun readKeyValueEmpty() {
        val source: Source = Buffer().also { it.writeString("\n") }
        val (key, value) = source.readKeyValue()
        assertEquals(key, null)
        assertEquals(value, null)
    }

    @Test
    fun readKeyValueMinimal() {
        val source: Source = Buffer().also { it.writeString("k=v") }
        val (key, value) = source.readKeyValue()
        assertEquals(key, "k")
        assertEquals(value, "v")
    }

    @Test
    fun readKeyValueWithSpace() {
        val source: Source = Buffer().also { it.writeString(" key = value ") }
        val (key, value) = source.readKeyValue()
        assertEquals(key, "key")
        assertEquals(value, "value")
    }

    @Test
    fun readKeyValueWithoutKey() {
        val source: Source = Buffer().also { it.writeString("= value") }
        val (key, value) = source.readKeyValue()
        assertEquals(key, null)
        assertEquals(value, "value")
    }

    @Test
    fun readKeyValueWithoutValue() {
        val source: Source = Buffer().also { it.writeString("key=") }
        val (key, value) = source.readKeyValue()
        assertEquals(key, "key")
        assertEquals(value, null)
    }

    @Test
    fun readKeyValueMultiline() {
        val source: Source = Buffer().also { it.writeString("key\t=value\nkey=value\n\rkey=\tvalue\t") }
        while (source.request(MIN_KEY_VALUE_LENGTH)) {
            val (key, value) = source.readKeyValue()
            assertEquals(key, "key")
            assertEquals(value, "value")
        }
    }

}