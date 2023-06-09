package ru.freeit.crazytraining.training.viewmodel_states

import android.content.Context
import android.widget.TextView

class TrainingTextState(
    private val title: Int,
    private val date: String
) {

    fun title(ctx: Context) = ctx.getString(title)
    fun date(view: TextView) {
        view.text = date
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is TrainingTextState) return false

        return title == other.title && date == other.date
    }

    override fun hashCode(): Int {
        var result = title
        result = 31 * result + date.hashCode()
        return result
    }

    override fun toString(): String {
        return "{ title -> $title, date -> $date }"
    }

}