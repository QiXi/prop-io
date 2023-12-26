# prop-io
Kotlin multiplatform library for reading and writing files in the format `type.key=value`

This library provides a combination of the [`datastore-preferences`](https://developer.android.com/reference/kotlin/androidx/datastore/preferences/core/package-summary.html) interface and the data format as [`.properties`](https://ru.wikipedia.org/wiki/.properties) based on a single [`kotlinx-io`](https://github.com/Kotlin/kotlinx-io) dependency

## Format
Comment lines in .prop files are denoted by the number sign (#) or the exclamation mark (!) as the first non blank character, in which all remaining text on that line is ignored.
Example file
```
# Comment
! Comment
key=value
# key for a String preference
s.name=String value
# key for a Float preference
f.rating=6.0
# key for a Int preference
i.votes=0
```

## Supported Types
```
s. - String
i. - Int
b. - Boolean
f. - Float
l. - Long
d. - Double
```

## Usage

```kotlin
interface Config {
    val termsVersion: Int
}

class ConfigRepository(
    private val filePath: Path,
) : Config {

    override var termsVersion = 0
    private val scope = CoroutineScope(Dispatchers.Default)

    private object Keys {
        val termsVersion = intKey("tv")
    }

    fun load() {
        scope.launch {
            val prop = mutablePreferencesOf().apply { readFrom(filePath) }
            termsVersion = prop[Keys.termsVersion] ?: 0
        }
    }

    fun saveTermsVersion() {
        val prop = mutablePreferencesOf().apply { readFrom(filePath) }
        prop[Keys.termsVersion] = AppConfig.termsVersion
        prop.writeTo(filePath)
    }

}
```