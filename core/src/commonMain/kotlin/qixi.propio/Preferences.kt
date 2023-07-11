package qixi.propio

abstract class Preferences internal constructor() {

    class Key<T> internal constructor(val name: String) {
        override fun equals(other: Any?): Boolean {
            return if (other is Key<*>) name == other.name else false
        }

        override fun hashCode(): Int = name.hashCode()

        override fun toString(): String = name
    }

    class Pair<T> internal constructor(internal val key: Key<T>, internal val value: T)

    abstract operator fun <T> contains(key: Key<T>): Boolean

    abstract operator fun <T> get(key: Key<T>): T?

    abstract fun asMap(): Map<Key<*>, Any>

}