package ru.freeit.crazytraining.exercise.detail.model

import androidx.annotation.StringRes
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseMeasuredValueState
import ru.freeit.crazytraining.training.dialogs.viewmodel_states.MeasuredValuesState

enum class ExerciseMeasuredValueModel(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val unit: Int,
    val measuredValuesState: MeasuredValuesState
) {
    QUANTITY(
        R.string.quantity,
        R.string.quantity_description,
        -1,
        MeasuredValuesState.Quantity
    ),
    DISTANCE(
        R.string.distance,
        R.string.distance_description,
        R.string.kilometers_meteres,
        MeasuredValuesState.Distance
    ),
    TIME(
        R.string.time,
        R.string.time_description,
        R.string.hours_minutes,
        MeasuredValuesState.Time
    );

    companion object {
        val measuredStates: List<ExerciseMeasuredValueState>
            get() = values().mapIndexed { index, model ->
                ExerciseMeasuredValueState(model, index == 0)
            }
    }
}