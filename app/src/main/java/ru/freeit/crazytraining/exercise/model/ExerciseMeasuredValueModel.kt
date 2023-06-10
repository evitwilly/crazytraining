package ru.freeit.crazytraining.exercise.model

import androidx.annotation.StringRes
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.exercise.viewmodel_states.ExerciseMeasuredValueState

enum class ExerciseMeasuredValueModel(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val unit: Int
) {
    QUANTITY(
        R.string.quantity,
        R.string.quantity_description,
        -1
    ),
    DISTANCE(
        R.string.distance,
        R.string.distance_description,
        R.string.kilometers_meteres
    ),
    TIME(
        R.string.time,
        R.string.time_description,
        R.string.hours_minutes
    );

    companion object {
        val measuredStates: List<ExerciseMeasuredValueState>
            get() = values().mapIndexed { index, model ->
                ExerciseMeasuredValueState(model, index == 0)
            }
    }
}