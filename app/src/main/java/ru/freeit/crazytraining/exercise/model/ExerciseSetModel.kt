package ru.freeit.crazytraining.exercise.model

import android.widget.TextView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetTableDb
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel

class ExerciseSetModel(
    private val id: Int = 0,
    private val amount: Int,
    private val millis: Long,
    private val exerciseId: Int = 0,
    private val measuredValueModel: ExerciseMeasuredValueModel,
    private val dateString: String = "",
    private val timeString: String = ""
) {
    val database: ExerciseSetTableDb
        get() = ExerciseSetTableDb(
            id = id,
            amount = amount,
            millis = millis,
            exercise_id = exerciseId,
            measuredValueModel = measuredValueModel.ordinal,
            dateString = dateString,
            timeString = timeString
        )

    fun bindAmount(view: TextView, number: Int) {
        view.text = view.context.resources.getQuantityString(R.plurals.set_title, amount, number, amount)
    }

    fun bindTime(view: TextView) {
        view.text = timeString
    }

}
