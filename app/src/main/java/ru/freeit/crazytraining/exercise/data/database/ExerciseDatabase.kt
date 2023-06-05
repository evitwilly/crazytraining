package ru.freeit.crazytraining.exercise.data.database

import android.database.Cursor
import ru.freeit.crazytraining.core.database.CoreDatabase
import ru.freeit.crazytraining.core.database.CoreSQLiteOpenHelper

class ExerciseDatabase(db: CoreSQLiteOpenHelper) : CoreDatabase<ExerciseTableDb>(db) {

    override val defaultItem = ExerciseTableDb()

    override fun item(cursor: Cursor): ExerciseTableDb {
        return ExerciseTableDb().fromCursor(cursor)
    }

}