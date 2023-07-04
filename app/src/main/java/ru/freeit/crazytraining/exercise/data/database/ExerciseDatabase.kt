package ru.freeit.crazytraining.exercise.data.database

import ru.freeit.crazytraining.core.database.CoreDatabase
import ru.freeit.crazytraining.core.database.CoreSQLiteOpenHelper

class ExerciseDatabase(db: CoreSQLiteOpenHelper) : CoreDatabase<ExerciseTableDb>(db) {

    override val defaultItem = ExerciseTableDb()

}

