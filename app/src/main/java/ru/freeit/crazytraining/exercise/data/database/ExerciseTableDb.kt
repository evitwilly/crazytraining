package ru.freeit.crazytraining.exercise.data.database

import android.content.ContentValues
import android.database.Cursor
import ru.freeit.crazytraining.core.database.TableColumnDb
import ru.freeit.crazytraining.core.database.TableDb

class ExerciseTableDb(
    private val icon: Int = -1,
    private val color: Int = -1,
    private val title: String = "",
    private val measuredValue: Int = -1,
    id: Int = 0,
) : TableDb(id) {
    override val name: String = "exercise"

    private val iconColumn = TableColumnDb.Integer(column_icon)
    private val colorColumn = TableColumnDb.Integer(column_color)
    private val titleColumn = TableColumnDb.Text(column_title)
    private val measuredValueColumn = TableColumnDb.Integer(column_measured_value)

    override val columns = listOf(iconColumn, colorColumn, titleColumn, measuredValueColumn)

    override val contentValues: ContentValues
        get() = ContentValues().apply {
            put(column_icon, icon)
            put(column_color, color)
            put(column_title, title)
            put(column_measured_value, measuredValue)
        }

    fun fromCursor(cursor: Cursor) =
        ExerciseTableDb(
            iconColumn.value(cursor),
            colorColumn.value(cursor),
            titleColumn.value(cursor),
            measuredValueColumn.value(cursor),
            id(cursor),
        )

    override fun toString(): String =
        "{ id = $id, $column_icon = $icon, $column_color = $color, $column_title = $title, $column_measured_value = $measuredValue }"

    private companion object {
        const val column_icon = "icon"
        const val column_color = "color"
        const val column_title = "title"
        const val column_measured_value = "measured_value"
    }

}