package ru.freeit.crazytraining.core.database

import android.database.sqlite.SQLiteDatabase

abstract class CoreDatabase<T : TableDb>(database: CoreSQLiteOpenHelper) {

    private val sqliteDb: SQLiteDatabase = database.writableDatabase

    abstract val defaultItem: T

    fun items(selection: SQLiteSelection? = null) : List<T> {
        val cursor = defaultItem.cursor(sqliteDb, selection)
        val list = mutableListOf<T>()
        while (cursor.moveToNext()) {
            list.add(defaultItem.fromCursor(cursor) as T)
        }
        return list
    }

    fun save(item: T) {
        sqliteDb.insert(item.name, null, item.contentValues)
    }

    fun update(item: T, selection: SQLiteSelection = SQLiteSelection(arrayOf(TableDb.column_id to item.id.toString()))) {
        sqliteDb.update(item.name, item.contentValues, selection.sqliteSelectionString, selection.sqliteSelectionArgs)
    }

    fun delete(item: T, selection: SQLiteSelection = SQLiteSelection(arrayOf(TableDb.column_id to item.id.toString()))) {
        sqliteDb.delete(item.name, selection.sqliteSelectionString, selection.sqliteSelectionArgs)
    }

}