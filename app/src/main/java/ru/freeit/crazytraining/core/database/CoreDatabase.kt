package ru.freeit.crazytraining.core.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

abstract class CoreDatabase<T : TableDb>(database: CoreSQLiteOpenHelper) {

    protected val sqliteDb: SQLiteDatabase = database.writableDatabase

    abstract val defaultItem: T
    abstract fun item(cursor: Cursor) : T

    fun items() : List<T> {
        val cursor = defaultItem.cursor(sqliteDb)
        val list = mutableListOf<T>()
        while (cursor.moveToNext()) {
            list.add(item(cursor))
        }
        return list
    }

    fun save(item: T) {
        sqliteDb.insert(item.name, null, item.contentValues)
    }

    fun delete(item: T) {
        sqliteDb.delete(item.name, "${TableDb.column_id} = ?", arrayOf(item.id.toString()))
    }

}