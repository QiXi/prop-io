package qixi.propio

fun preferencesOf(vararg pairs: Preferences.Pair<*>): Preferences {
    return mutablePreferencesOf(*pairs)
}

fun mutablePreferencesOf(vararg pairs: Preferences.Pair<*>): MutablePreferences {
    return MutablePreferences().apply { putAll(*pairs) }
}

fun intKey(name: String): Preferences.Key<Int> = Preferences.Key(name)

fun doubleKey(name: String): Preferences.Key<Double> = Preferences.Key(name)

fun stringKey(name: String): Preferences.Key<String> = Preferences.Key(name)

fun booleanKey(name: String): Preferences.Key<Boolean> = Preferences.Key(name)

fun floatKey(name: String): Preferences.Key<Float> = Preferences.Key(name)

fun longKey(name: String): Preferences.Key<Long> = Preferences.Key(name)