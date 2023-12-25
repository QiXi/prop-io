# prop-io
Kotlin multiplatform library for reading and writing files in the format `type.key=value`

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