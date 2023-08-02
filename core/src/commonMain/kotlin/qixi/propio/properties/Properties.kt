package qixi.propio.properties

import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.writeString
import qixi.propio.MIN_KEY_VALUE_LENGTH
import qixi.propio.readKeyValue

open class Properties {

    private val map: MutableMap<String, String?> = mutableMapOf()

    fun size(): Int = map.size

    fun isEmpty(): Boolean = map.isEmpty()

    operator fun contains(key: String): Boolean = map.containsKey(key)

    fun remove(key: String) = map.remove(key)

    fun clear() = map.clear()

    operator fun get(key: String): String? {
        return map[key]
    }

    operator fun set(key: String, value: String?) {
        map[key] = value
    }

    fun getProperty(key: String, defaultValue: String): String {
        return map[key] ?: defaultValue
    }

    fun setProperty(key: String, value: String) = set(key, value)

    fun put(key: String, value: String?) = set(key, value)

    fun put(key: String, value: Number) {
        put(key, value.toString())
    }

    fun put(key: String, value: Boolean) {
        put(key, value.toString())
    }

    fun put(key: String, value: Set<String>, separator: CharSequence) {
        put(key, value.joinToString(separator))
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return StringParser.getInt(map[key], defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return StringParser.getFloat(map[key], defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return StringParser.getLong(map[key], defaultValue)
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return this.getProperty(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return StringParser.getBoolean(map[key], defaultValue)
    }

    fun getStringSet(key: String, separator: String = ","): Set<String> {
        return StringParser.getStringSet(map[key], separator)
    }

    fun readFrom(source: Source) {
        while (source.request(MIN_KEY_VALUE_LENGTH)) {
            val (key, value) = source.readKeyValue()
            if (key != null && value != null) {
                map[key] = value
            }
        }
    }

    fun writeTo(sink: Sink) {
        map.entries.forEach {
            val line = "${it.key}=${it.value}\n"
            sink.writeString(line)
        }
    }

    override fun toString(): String {
        return map.entries.joinToString(
            separator = ",\n",
            prefix = "{\n",
            postfix = "\n}"
        ) { entry -> "  ${entry.key} = ${entry.value}" }
    }

}