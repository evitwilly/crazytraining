package ru.freeit.crazytraining.core.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

abstract class TableDb(val id: Int = 0) {
    abstract val name: String
    abstract val contentValues: ContentValues
    protected abstract val columns: List<TableColumnDb<*>>

    fun create(db: SQLiteDatabase?) {
        val columnsString = columns.joinToString(",") { it.sqliteColumnDefinitionString }
        db?.execSQL("create table if not exists $name ($column_id integer primary key autoincrement not null, $columnsString)")
    }

    fun drop(db: SQLiteDatabase?) {
        db?.execSQL("drop table $name if exists")
    }

    fun cursor(db: SQLiteDatabase): Cursor {
        return db.query(name, arrayOf(column_id) + columns.map { it.name }.toTypedArray(), null, null, null, null, column_id)
    }

    protected fun id(cursor: Cursor): Int {
        val index = cursor.getColumnIndex(column_id)
        return if (index >= 0) cursor.getInt(index) else -1
    }

    companion object {
        const val column_id = "id"
    }

}