package ru.freeit.crazytraining.exercise.data.database

import android.database.Cursor
import ru.freeit.crazytraining.core.database.CoreDatabase
import ru.freeit.crazytraining.core.database.CoreSQLiteOpenHelper

class ExerciseSetDatabase(db: CoreSQLiteOpenHelper) : CoreDatabase<ExerciseSetTableDb>(db) {

    override val defaultItem = ExerciseSetTableDb()

    override fun item(cursor: Cursor) = ExerciseSetTableDb().fromCursor(cursor)

    fun itemsByExerciseId(id: Int) : List<ExerciseSetTableDb> {
        val cursor = defaultItem.cursorByExerciseId(sqliteDb, id)
        val list = mutableListOf<ExerciseSetTableDb>()
        while (cursor.moveToNext()) {
            list.add(item(cursor))
        }
        return list
    }

}