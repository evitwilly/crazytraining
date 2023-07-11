package ru.freeit.crazytraining.core.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetTableDb
import ru.freeit.crazytraining.exercise.data.database.ExerciseTableDb
import ru.freeit.crazytraining.training.data.database.TrainingTableDb

class CoreSQLiteOpenHelper(ctx: Context) : SQLiteOpenHelper(ctx, name, null, version) {

    private val tables = listOf(
        ExerciseTableDb(),
        ExerciseSetTableDb(),
        TrainingTableDb()
    )

    override fun onCreate(database: SQLiteDatabase?) {
        tables.forEach { table -> table.create(database) }
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion == 1 && newVersion == 2) {
            ExerciseTableDb().migration2(database)
        }
    }

    private companion object {
        const val name = "app_database.db"
        const val version = 3
    }

}