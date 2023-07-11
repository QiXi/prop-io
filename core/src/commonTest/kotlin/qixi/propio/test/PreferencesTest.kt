package qixi.propio.test

import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.writeString
import qixi.propio.properties.Properties
import kotlin.test.Test
import kotlin.test.assertEquals

class PreferencesTest {

    @Test
    fun testDuplicate() {
        val source: Source = Buffer().also { it.writeString("duplicateKey = first\nduplicateKey = second") }
        val store = Properties()
        store.readFrom(source)
        assertEquals(store["duplicateKey"], "second")
    }


}