package ru.freeit.crazytraining.core

import android.app.Application
import ru.freeit.crazytraining.core.cache.PersistenceSimpleDataStorage
import ru.freeit.crazytraining.core.database.CoreSQLiteOpenHelper
import ru.freeit.crazytraining.core.theming.CoreThemeManager
import ru.freeit.crazytraining.core.theming.typeface.TypefaceManager

class App: Application() {

    lateinit var persistenceSimpleDataStorage: PersistenceSimpleDataStorage
    lateinit var themeManager: CoreThemeManager
    lateinit var typefaceManager: TypefaceManager
    lateinit var coreSQLiteOpenHelper: CoreSQLiteOpenHelper

    override fun onCreate() {
        super.onCreate()
        persistenceSimpleDataStorage = PersistenceSimpleDataStorage(this)
        themeManager = CoreThemeManager(persistenceSimpleDataStorage)
        typefaceManager = TypefaceManager(this.assets)
        coreSQLiteOpenHelper = CoreSQLiteOpenHelper(this)
    }

}