package ru.freeit.crazytraining.exercise.data.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.freeit.crazytraining.core.database.TableColumnDb
import ru.freeit.crazytraining.core.database.TableDb
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseTableDb(
    private val title: String = "",
    private val unit: Int = -1,
    id: Int = 0,
) : TableDb(id) {

    val model: ExerciseModel
        get() = ExerciseModel(
            title = title,
            unit = ExerciseUnitModel.values()[unit],
            id = id
        )

    override val name: String = "exercise"

    private val titleColumn = TableColumnDb.Text(column_title)
    private val unitColumn = TableColumnDb.Integer(column_measured_value)

    override val columns = listOf(titleColumn, unitColumn)

    override val contentValues: ContentValues
        get() = ContentValues().apply {
            put(column_title, title)
            put(column_measured_value, unit)
        }

    override fun fromCursor(cursor: Cursor) =
        ExerciseTableDb(
            titleColumn.value(cursor),
            unitColumn.value(cursor).toInt(),
            id(cursor),
        )

    fun migration2(db: SQLiteDatabase?) {
        if (db == null) return
        with(db) {
            beginTransaction()
            val temporary = "temp_table"
            create(this, tableName = temporary)
            copy(this, fromTableName = name, toTableName = temporary)
            drop(this)
            execSQL("alter table $temporary rename to $name")
            setTransactionSuccessful()
            endTransaction()
        }
    }

    override fun toString(): String =
        "{ id = $id, $column_title = $title, $column_measured_value = $unit }"

    private companion object {
        const val column_title = "title"
        const val column_measured_value = "measured_value"
    }

}