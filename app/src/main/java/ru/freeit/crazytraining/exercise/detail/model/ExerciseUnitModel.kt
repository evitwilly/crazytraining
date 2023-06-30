package ru.freeit.crazytraining.exercise.detail.model

import androidx.annotation.StringRes
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseUnitListItemState
import ru.freeit.crazytraining.training.dialogs.viewmodel_states.ExerciseUnitDialogState

enum class ExerciseUnitModel(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val value: Int,
    val state: ExerciseUnitDialogState
) {
    QUANTITY(
        R.string.quantity,
        R.string.quantity_description,
        -1,
        ExerciseUnitDialogState.Quantity
    ),
    DISTANCE(
        R.string.distance,
        R.string.distance_description,
        R.string.kilometers_meteres,
        ExerciseUnitDialogState.Distance
    ),
    TIME(
        R.string.time,
        R.string.time_description,
        R.string.minutes_seconds,
        ExerciseUnitDialogState.Time
    );

    companion object {
        val measuredStates: List<ExerciseUnitListItemState>
            get() = values().mapIndexed { index, model ->
                ExerciseUnitListItemState(model, index == 0)
            }
    }

}