package ru.freeit.crazytraining.exercise.data.database

import android.database.Cursor
import ru.freeit.crazytraining.core.database.CoreDatabase
import ru.freeit.crazytraining.core.database.CoreSQLiteOpenHelper

class ExerciseSetDatabase(db: CoreSQLiteOpenHelper) : CoreDatabase<ExerciseSetTableDb>(db) {

    override val defaultItem = ExerciseSetTableDb()

    override fun item(cursor: Cursor) = ExerciseSetTableDb().fromCursor(cursor)

    fun deleteByDate(date: String) {
        defaultItem.deleteByDate(sqliteDb, date)
    }

    fun itemsByDate(date: String) : List<ExerciseSetTableDb> {
        val cursor = defaultItem.cursorByDate(sqliteDb, date)
        val list = mutableListOf<ExerciseSetTableDb>()
        while (cursor.moveToNext()) {
            list.add(item(cursor))
        }
        return list
    }

    fun itemsByExerciseId(id: Int, date: String) : List<ExerciseSetTableDb> {
        val cursor = defaultItem.cursorByExerciseId(sqliteDb, id, date)
        val list = mutableListOf<ExerciseSetTableDb>()
        while (cursor.moveToNext()) {
            list.add(item(cursor))
        }
        return list
    }

}