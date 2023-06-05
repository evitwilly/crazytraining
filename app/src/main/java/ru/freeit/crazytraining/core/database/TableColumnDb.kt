package ru.freeit.crazytraining.core.database

import android.database.Cursor

sealed class TableColumnDb<T>(val name: String, private val type: String) {

    val sqliteColumnDefinitionString: String
        get() = "$name $type not null"

    abstract fun value(cursor: Cursor): T

    class Integer(name: String) : TableColumnDb<Int>(name, "integer") {
        override fun value(cursor: Cursor): Int {
            val index = cursor.getColumnIndex(name)
            return if (index >= 0) cursor.getInt(index) else 0
        }
    }

    class Real(name: String) : TableColumnDb<Float>(name, "real") {
        override fun value(cursor: Cursor): Float {
            val index = cursor.getColumnIndex(name)
            return if (index >= 0) cursor.getFloat(index) else 0f
        }
    }

    class Text(name: String) : TableColumnDb<String>(name, "text") {
        override fun value(cursor: Cursor): String {
            val index = cursor.getColumnIndex(name)
            return if (index >= 0) cursor.getString(index) else ""
        }
    }

}