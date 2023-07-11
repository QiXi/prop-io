package qixi.propio

class MutablePreferences internal constructor(
    internal val map: MutableMap<Key<*>, Any> = mutableMapOf(),
) : Preferences() {

    override operator fun <T> contains(key: Key<T>): Boolean {
        return map.containsKey(key)
    }

    override operator fun <T> get(key: Key<T>): T? {
        return map[key] as T?
    }

    operator fun <T> set(key: Key<T>, value: T) {
        setOrRemove(key, value)
    }

    internal fun setOrRemove(key: Key<*>, value: Any?) {
        when (value) {
            null -> remove(key)
            else -> map[key] = value
        }
    }

    fun putAll(vararg pairs: Pair<*>) {
        pairs.forEach { setOrRemove(it.key, it.value) }
    }

    fun <T> remove(key: Key<T>): T {
        return map.remove(key) as T
    }

    fun clear() = map.clear()

    override fun asMap(): Map<Key<*>, Any> {
        val result = mutableMapOf<Key<*>, Any>()
        map.entries.forEach { entry ->
            result[entry.key] = entry.value
        }
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other !is MutablePreferences) {
            return false
        }
        if (other.map === map) return true
        if (other.map.size != map.size) return false
        return other.map.all { otherEntry ->
            map[otherEntry.key]?.let { value ->
                otherEntry.value == value
            } ?: false
        }
    }

    override fun hashCode(): Int {
        return map.entries.sumOf { entry ->
            entry.value.hashCode()
        }
    }

    override fun toString(): String {
        return map.entries.joinToString(
            separator = ",\n",
            prefix = "{\n",
            postfix = "\n}"
        ) { entry ->
            "  ${entry.key.name} = ${entry.value}"
        }
    }

}