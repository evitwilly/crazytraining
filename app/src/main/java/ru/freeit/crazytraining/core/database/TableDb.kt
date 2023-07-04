package ru.freeit.crazytraining.core.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

abstract class TableDb(val id: Int = 0) {
    abstract val name: String
    abstract val contentValues: ContentValues
    protected abstract val columns: List<TableColumnDb<*>>

    abstract fun fromCursor(cursor: Cursor) : TableDb

    fun create(db: SQLiteDatabase?, tableName: String = name) {
        val columnsString = columns.joinToString(",") { it.sqliteColumnDefinitionString }
        db?.execSQL("create table if not exists $tableName ($column_id integer primary key autoincrement not null, $columnsString)")
    }

    fun copy(db: SQLiteDatabase?, fromTableName: String, toTableName: String) {
        val columnsString = columns.joinToString(",") { it.name }
        db?.execSQL("insert into $toTableName ($column_id, $columnsString) select $column_id, $columnsString from $fromTableName")
    }

    fun drop(db: SQLiteDatabase?) {
        db?.execSQL("drop table if exists $name")
    }

    fun cursor(db: SQLiteDatabase, selection: SQLiteSelection? = null): Cursor {
        return db.query(name, arrayOf(column_id) + columns.map { it.name }.toTypedArray(), selection?.sqliteSelectionString, selection?.sqliteSelectionArgs, null, null, column_id)
    }

    protected fun id(cursor: Cursor): Int {
        val index = cursor.getColumnIndex(column_id)
        return if (index >= 0) cursor.getInt(index) else -1
    }

    companion object {
        const val column_id = "id"
    }

}