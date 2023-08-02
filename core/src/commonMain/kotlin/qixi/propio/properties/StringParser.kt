package qixi.propio.properties

object StringParser {

    fun getInt(value: String?, defaultValue: Int = 0): Int {
        return try {
            value?.toInt() ?: defaultValue
        } catch (nfe: NumberFormatException) {
            defaultValue
        }
    }

    fun getFloat(value: String?, defaultValue: Float = 0f): Float {
        return try {
            value?.toFloat() ?: defaultValue
        } catch (nfe: NumberFormatException) {
            defaultValue
        }
    }

    fun getLong(value: String?, defaultValue: Long): Long {
        return try {
            value?.toLong() ?: defaultValue
        } catch (nfe: NumberFormatException) {
            defaultValue
        }
    }

    fun getBoolean(value: String?, defaultValue: Boolean): Boolean {
        return when (value) {
            "true" -> true
            "false" -> false
            else -> defaultValue
        }
    }

    fun getStringSet(value: String?, separator: String = ","): Set<String> {
        return value?.split(separator)?.toSet() ?: emptySet()
    }

}