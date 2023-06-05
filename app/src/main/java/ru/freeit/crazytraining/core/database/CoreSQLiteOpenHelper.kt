package ru.freeit.crazytraining.core.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.freeit.crazytraining.exercise.data.database.ExerciseTableDb

class CoreSQLiteOpenHelper(ctx: Context) : SQLiteOpenHelper(ctx, name, null, version) {

    private val tables = listOf(ExerciseTableDb())

    override fun onCreate(database: SQLiteDatabase?) {
        tables.forEach { table -> table.create(database) }
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        tables.forEach { table -> table.drop(database) }
        onCreate(database)
    }

    private companion object {
        const val name = "app_database.db"
        const val version = 1
    }

}