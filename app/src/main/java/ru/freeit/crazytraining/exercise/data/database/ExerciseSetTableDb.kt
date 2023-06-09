package ru.freeit.crazytraining.exercise.data.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.freeit.crazytraining.core.database.TableColumnDb
import ru.freeit.crazytraining.core.database.TableDb
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class ExerciseSetTableDb(
    private val amount: Int = 0,
    private val date: Long = 0L,
    private val exercise_id: Int = 0,
    id: Int = 0,
): TableDb(id) {

    val model: ExerciseSetModel
        get() = ExerciseSetModel(amount, date)

    fun model(exerciseId: Int, repo: CalendarRepository) = ExerciseSetModel(
        amount = amount,
        date = date,
        exerciseId = exerciseId,
        timeString = repo.timeStringFrom(date)
    )

    override val name: String = "exercise_sets_table"

    private val amountColumn = TableColumnDb.Integer(column_amount)
    private val dateColumn = TableColumnDb.Integer(column_date)
    private val exerciseId = TableColumnDb.Integer(column_exercise_id)

    override val columns = listOf(amountColumn, dateColumn, exerciseId)

    override val contentValues: ContentValues
        get() = ContentValues().apply {
            put(column_amount, amount)
            put(column_date, date)
            put(column_exercise_id, exercise_id)
        }

    fun cursorByExerciseId(db: SQLiteDatabase, id: Int) = cursor(db, "$exerciseId = ?", arrayOf(id.toString()))

    fun fromCursor(cursor: Cursor) =
        ExerciseSetTableDb(
            amountColumn.value(cursor).toInt(),
            dateColumn.value(cursor),
            exerciseId.value(cursor).toInt(),
            id(cursor),
        )

    private companion object {
        const val column_amount = "amount"
        const val column_date = "date"
        const val column_exercise_id = "exercise_id"
    }

}