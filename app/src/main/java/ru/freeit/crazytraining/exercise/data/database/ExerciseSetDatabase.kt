package ru.freeit.crazytraining.exercise.data.database

import ru.freeit.crazytraining.core.database.CoreDatabase
import ru.freeit.crazytraining.core.database.CoreSQLiteOpenHelper
import ru.freeit.crazytraining.core.database.SQLiteSelection

class ExerciseSetDatabase(db: CoreSQLiteOpenHelper) : CoreDatabase<ExerciseSetTableDb>(db) {

    override val defaultItem = ExerciseSetTableDb()

    fun deleteByDate(date: String) {
        delete(defaultItem, SQLiteSelection().select(ExerciseSetTableDb.column_date_string, date))
    }

    fun itemsByDate(date: String) : List<ExerciseSetTableDb> {
        return items(SQLiteSelection().select(ExerciseSetTableDb.column_date_string, date))
    }

    fun itemsByTrainingId(id: Int) : List<ExerciseSetTableDb> {
        return items(SQLiteSelection().select(ExerciseSetTableDb.column_training_id, id.toString()))
    }

}