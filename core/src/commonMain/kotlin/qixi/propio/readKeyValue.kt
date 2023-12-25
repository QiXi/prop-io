package qixi.propio

import kotlinx.io.Source
import kotlinx.io.readString
import kotlinx.io.startsWith

const val MIN_KEY_VALUE_LENGTH = 3L

fun Source.readKeyValue(): Pair<String?, String?> {
    if (!request(MIN_KEY_VALUE_LENGTH)) return Pair(null, null)
    val peekSource = peek()
    var offset = 0L
    var keyLength = 0L
    var hasSeparator = false
    var isComment = false
    var newlineSize = 0L
    while (peekSource.request(1)) {
        val b = peekSource.readByte().toInt()
        if (offset == 0L && (b == '#'.code || b == '!'.code)) {
            isComment = true
        } else if (!isComment && !hasSeparator && (b == '='.code || b == ':'.code)) {
            keyLength = offset
            hasSeparator = true
        } else if (b == '\n'.code) {
            newlineSize = 1L
            break
        } else if (b == '\r'.code) {
            if (peekSource.startsWith('\n'.code.toByte())) {
                newlineSize = 2L
                break
            }
        }
        offset++
    }
    return if (isComment) {
        val comment = readString(offset)
        skip(newlineSize)
        Pair(null, comment)
    } else if (!hasSeparator) {
        val line = readString(offset).trim()
        skip(newlineSize)
        Pair(null, line.ifBlank { null })
    } else {
        val key = if (keyLength > 0) readString(keyLength).trim() else null
        skip(1L) // '=' or ':'
        val valueLength = offset - keyLength - 1
        val value = if (valueLength > 0) readString(valueLength).trim() else null
        skip(newlineSize)
        Pair(key, value)
    }
}