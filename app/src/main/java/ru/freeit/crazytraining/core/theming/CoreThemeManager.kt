package ru.freeit.crazytraining.core.theming

import ru.freeit.crazytraining.core.cache.PersistentIntStorage

class CoreThemeManager(private val themeDataStorage: PersistentIntStorage) {

    private val listeners = mutableListOf<(CoreTheme) -> Unit>()

    private var currentTheme = CoreTheme.values()[themeDataStorage.int(theme_key, CoreTheme.LIGHT.ordinal)]

    val selected_theme: CoreTheme
        get() = currentTheme

    fun listenForThemeChanges(listener: (CoreTheme) -> Unit) {
        listeners.add(listener)
        listener.invoke(currentTheme)
    }

    fun doNotListenForThemeChanges(listener: (CoreTheme) -> Unit) {
        listeners.remove(listener)
    }

    fun changeTheme(theme: CoreTheme) {
        currentTheme = theme
        themeDataStorage.save(theme_key, currentTheme.ordinal)
        listeners.forEach { listener -> listener.invoke(currentTheme) }
    }

    companion object {
        private const val theme_key = "CoreThemeManager_key"
    }

}