package qixi.propio.test

import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.writeString
import qixi.propio.mutablePreferencesOf
import qixi.propio.readFrom
import qixi.propio.stringKey
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertiesTest {

    @Test
    fun testDuplicate() {
        val source: Source = Buffer().also { it.writeString("duplicateKey = first\nduplicateKey = second") }
        val preferences = mutablePreferencesOf()
        preferences.readFrom(source)
        val key = stringKey("duplicateKey")
        assertEquals(preferences.map[key], "second")
    }

}