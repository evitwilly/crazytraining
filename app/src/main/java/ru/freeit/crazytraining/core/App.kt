package ru.freeit.crazytraining.core

import android.app.Application
import ru.freeit.crazytraining.core.cache.PersistenceSimpleDataStorage
import ru.freeit.crazytraining.core.theming.CoreThemeManager

class App: Application() {

    private lateinit var persistenceSimpleDataStorage: PersistenceSimpleDataStorage
    lateinit var themeManager: CoreThemeManager

    override fun onCreate() {
        super.onCreate()
        persistenceSimpleDataStorage = PersistenceSimpleDataStorage(this)
        themeManager = CoreThemeManager(persistenceSimpleDataStorage)
    }

}