package qixi.propio.repository

import kotlinx.io.Sink
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import qixi.propio.MutablePreferences
import qixi.propio.Preferences
import qixi.propio.mutablePreferencesOf
import qixi.propio.readFrom
import qixi.propio.writeTo

abstract class DataRepository<K, V> {

    protected val repository = mutableMapOf<K, V>()
    val data: Collection<V> = repository.values
    val size: Int get() = repository.size

    operator fun contains(key: K): Boolean = repository.containsKey(key)

    operator fun get(key: K): V? = repository[key]

    protected abstract fun getPath(data: V): Path?

    protected abstract fun createSink(data: V): Sink

    protected abstract fun removeSource(data: V)

    protected abstract fun readData(data: V, prop: Preferences)

    protected abstract fun writeData(prop: MutablePreferences, data: V)

    fun add(key: K, value: V?) {
        if (value == null) {
            remove(key)
        } else {
            repository[key] = value
            save(value)
        }
    }

    fun remove(key: K): V? {
        val data = repository.remove(key)
        if (data != null) removeSource(data)
        return data
    }

    open fun load(data: V): Boolean {
        val filePath = getPath(data) ?: return false
        val prop = mutablePreferencesOf()
        prop.readFrom(SystemFileSystem.source(filePath).buffered())
        readData(data, prop)
        return true
    }

    open fun save(data: V) {
        val prop = mutablePreferencesOf()
        writeData(prop, data)
        prop.writeTo(createSink(data))
    }

}