package ru.freeit.crazytraining.exercise.data.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.freeit.crazytraining.core.database.TableColumnDb
import ru.freeit.crazytraining.core.database.TableDb
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class ExerciseSetTableDb(
    private val amount: Int = 0,
    private val millis: Long = 0L,
    private val exercise_id: Int = 0,
    private val measuredValueModel: Int = 0,
    private val dateString: String = "",
    private val timeString: String = "",
    id: Int = 0,
): TableDb(id) {

    fun model() = ExerciseSetModel(
        id = id,
        amount = amount,
        millis = millis,
        exerciseId = exercise_id,
        measuredValueModel = ExerciseMeasuredValueModel.values()[measuredValueModel],
        dateString = dateString,
        timeString = timeString
    )

    override val name: String = "exercise_sets_table"

    private val amountColumn = TableColumnDb.Integer(column_amount)
    private val millisColumn = TableColumnDb.Integer(column_millis)
    private val exerciseId = TableColumnDb.Integer(column_exercise_id)
    private val measuredValueColumn = TableColumnDb.Integer(column_measured_value)
    private val dateStringColumn = TableColumnDb.Text(column_date_string)
    private val timeStringColumn = TableColumnDb.Text(column_time_string)

    override val columns = listOf(amountColumn, millisColumn, exerciseId, measuredValueColumn, dateStringColumn, timeStringColumn)

    override val contentValues: ContentValues
        get() = ContentValues().apply {
            put(column_amount, amount)
            put(column_millis, millis)
            put(column_exercise_id, exercise_id)
            put(column_measured_value, measuredValueModel)
            put(column_date_string, dateString)
            put(column_time_string, timeString)
        }

    fun cursorByExerciseId(db: SQLiteDatabase, exercise_id: Int, date: String) = cursor(db, "$column_exercise_id = ? and $column_date_string = ?", arrayOf(exercise_id.toString(), date))
    fun cursorByDate(db: SQLiteDatabase, date: String) = cursor(db, "$column_date_string = ?", arrayOf(date))

    fun deleteByDate(db: SQLiteDatabase, date: String) = db.delete(name, "$column_date_string = ?", arrayOf(date))

    fun fromCursor(cursor: Cursor) =
        ExerciseSetTableDb(
            amountColumn.value(cursor).toInt(),
            millisColumn.value(cursor),
            exerciseId.value(cursor).toInt(),
            measuredValueColumn.value(cursor).toInt(),
            dateStringColumn.value(cursor),
            timeStringColumn.value(cursor),
            id(cursor),
        )

    private companion object {
        const val column_amount = "amount"
        const val column_millis = "millis"
        const val column_exercise_id = "exercise_id"
        const val column_measured_value = "measured_value"
        const val column_date_string = "date"
        const val column_time_string = "time"
    }

}