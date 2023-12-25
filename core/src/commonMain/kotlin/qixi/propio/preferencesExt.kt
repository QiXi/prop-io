package qixi.propio

import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.writeString

fun MutablePreferences.readFrom(source: Source) {
    while (source.request(MIN_KEY_VALUE_LENGTH)) {
        val (key, value) = source.readKeyValue()
        if (!key.isNullOrEmpty() && value != null) {
            addEntry(key, value)
        }
    }
}

fun MutablePreferences.readFrom(filePath: Path) {
    this.readFrom(SystemFileSystem.source(filePath).buffered())
}

fun MutablePreferences.addEntry(name: String, value: String) {
    if (name.length < 3) { // no type
        map[stringKey(name)] = value
        return
    }
    val type = name.substring(0, 2)
    val key = name.substring(2)
    when (type) {
        "s." -> map[stringKey(key)] = value.replace("\\n", "\n")
        "i." -> map[intKey(key)] = value.toInt()
        "b." -> map[booleanKey(key)] = value.toBoolean()
        "f." -> map[floatKey(key)] = value.toFloat()
        "l." -> map[longKey(key)] = value.toLong()
        "d." -> map[doubleKey(key)] = value.toDouble()
        else -> map[stringKey(name)] = value
    }
}

fun Preferences.writeTo(sink: Sink) {
    val preferences = asMap()
    for ((key, value) in preferences) {
        val keyName: String = when (value) {
            is String -> "s.${key.name}"
            is Int -> "i.${key.name}"
            is Boolean -> "b.${key.name}"
            is Float -> "f.${key.name}"
            is Long -> "l.${key.name}"
            is Double -> "d.${key.name}"
            else -> key.name
        }
        val saveValue = when (value) {
            is String -> value.replace("\n", "\\n")
            else -> value
        }
        sink.writeString("$keyName=$saveValue\n")
    }
    sink.emit()
}

fun Preferences.writeTo(filePath: Path) {
    this.writeTo(SystemFileSystem.sink(filePath).buffered())
}