package ru.freeit.crazytraining.core.database

class SQLiteSelection(private val columns: Array<Pair<String, String>> = arrayOf()) {

    val sqliteSelectionString: String
        get() = columns.joinToString(" and ") { "${it.first} = ?" }

    val sqliteSelectionArgs: Array<String>
        get() = columns.map { it.second }.toTypedArray()

    fun select(columnName: String, columnValue: String) =
        SQLiteSelection(columns + arrayOf(columnName to columnValue))

}