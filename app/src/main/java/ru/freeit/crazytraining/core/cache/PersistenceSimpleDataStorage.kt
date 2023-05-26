package ru.freeit.crazytraining.core.cache

import android.content.Context

class PersistenceSimpleDataStorage(ctx: Context): PersistentIntStorage {

    private val readerSharedPreferences = ctx.getSharedPreferences(storage_name, Context.MODE_PRIVATE)
    private val editorSharedPreferences = readerSharedPreferences.edit()

    override fun int(key: String, default: Int): Int = readerSharedPreferences.getInt(key, default)

    override fun save(key: String, value: Int) {
        editorSharedPreferences.putInt(key, value).apply()
    }

    companion object {
        private const val storage_name = "persistence_simple_data_storage"
    }

}