package ru.freeit.crazytraining.training.data.database

import ru.freeit.crazytraining.core.database.CoreDatabase
import ru.freeit.crazytraining.core.database.CoreSQLiteOpenHelper

class TrainingDatabase(db: CoreSQLiteOpenHelper) : CoreDatabase<TrainingTableDb>(db) {

    override val defaultItem = TrainingTableDb()

}