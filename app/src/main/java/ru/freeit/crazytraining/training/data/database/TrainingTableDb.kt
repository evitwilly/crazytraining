package ru.freeit.crazytraining.training.data.database

import android.content.ContentValues
import android.database.Cursor
import ru.freeit.crazytraining.core.database.TableColumnDb
import ru.freeit.crazytraining.core.database.TableDb
import ru.freeit.crazytraining.training.model.TrainingModel

class TrainingTableDb(
    private val millis: Long = 0L,
    private val date: String = "",
    private val rating: Float = 4f,
    private val comment: String = "",
    private val active: Boolean = true,
    id: Int = 0
): TableDb(id) {

    val model: TrainingModel
        get() = TrainingModel(
            millis = millis,
            date = date,
            rating = rating,
            comment = comment,
            active = active,
            id = id
        )

    override val name: String = "training_table"

    private val millisColumn = TableColumnDb.Integer(column_millis)
    private val dateColumn = TableColumnDb.Text(column_date)
    private val ratingColumn = TableColumnDb.Real(column_rating)
    private val commentColumn = TableColumnDb.Text(column_comment)
    private val columnActive = TableColumnDb.Integer(column_active)

    override val columns = listOf(millisColumn, dateColumn, ratingColumn, commentColumn, columnActive)

    override val contentValues: ContentValues
        get() = ContentValues().apply {
            put(column_millis, millis)
            put(column_date, date)
            put(column_rating, rating)
            put(column_comment, comment)
            put(column_active, if (active) 1L else 0L)
        }

    override fun fromCursor(cursor: Cursor) =
        TrainingTableDb(
            millis = millisColumn.value(cursor),
            date = dateColumn.value(cursor),
            rating = ratingColumn.value(cursor),
            comment = commentColumn.value(cursor),
            active = columnActive.value(cursor) == 1L,
            id = id(cursor)
        )

    companion object {
        const val column_millis = "millis"
        const val column_date = "date"
        const val column_rating = "rating"
        const val column_comment = "comment"
        const val column_active = "active"
    }

}