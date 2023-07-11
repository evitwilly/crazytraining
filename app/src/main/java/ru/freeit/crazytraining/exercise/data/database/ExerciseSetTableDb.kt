package ru.freeit.crazytraining.exercise.data.database

import android.content.ContentValues
import android.database.Cursor
import ru.freeit.crazytraining.core.database.TableColumnDb
import ru.freeit.crazytraining.core.database.TableDb
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class ExerciseSetTableDb(
    private val amount: Int = 0,
    private val millis: Long = 0L,
    private val exercise_id: Int = 0,
    private val training_id: Int = 0,
    private val unit: Int = 0,
    private val dateString: String = "",
    private val timeString: String = "",
    id: Int = 0,
): TableDb(id) {

    val model: ExerciseSetModel
        get() = ExerciseSetModel(
            id = id,
            amount = amount,
            millis = millis,
            exerciseId = exercise_id,
            trainingId = training_id,
            unit = ExerciseUnitModel.values()[unit],
            dateString = dateString,
            timeString = timeString
        )

    override val name: String = "exercise_sets_table"

    private val amountColumn = TableColumnDb.Integer(column_amount)
    private val millisColumn = TableColumnDb.Integer(column_millis)
    private val exerciseId = TableColumnDb.Integer(column_exercise_id)
    private val trainingId = TableColumnDb.Integer(column_training_id, 0L)
    private val unitColumn = TableColumnDb.Integer(column_unit)
    private val dateStringColumn = TableColumnDb.Text(column_date_string)
    private val timeStringColumn = TableColumnDb.Text(column_time_string)

    override val columns = listOf(amountColumn, millisColumn, exerciseId, trainingId, unitColumn, dateStringColumn, timeStringColumn)

    override val contentValues: ContentValues
        get() = ContentValues().apply {
            put(column_amount, amount)
            put(column_millis, millis)
            put(column_exercise_id, exercise_id)
            put(column_training_id, training_id)
            put(column_unit, unit)
            put(column_date_string, dateString)
            put(column_time_string, timeString)
        }

    override fun fromCursor(cursor: Cursor) =
        ExerciseSetTableDb(
            amountColumn.value(cursor).toInt(),
            millisColumn.value(cursor),
            exerciseId.value(cursor).toInt(),
            trainingId.value(cursor).toInt(),
            unitColumn.value(cursor).toInt(),
            dateStringColumn.value(cursor),
            timeStringColumn.value(cursor),
            id(cursor),
        )

    companion object {
        const val column_amount = "amount"
        const val column_millis = "millis"
        const val column_exercise_id = "exercise_id"
        const val column_training_id = "training_id"
        const val column_unit = "measured_value"
        const val column_date_string = "date"
        const val column_time_string = "time"
    }

}