package ru.freeit.crazytraining.exercise.detail.model

import androidx.annotation.StringRes
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.ResourcesProvider
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseUnitListItemState
import ru.freeit.crazytraining.training.dialogs.viewmodel_states.ExerciseUnitDialogState

enum class ExerciseUnitModel(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val value: Int,
    val amountConverterString: (resources: ResourcesProvider, amount: Int) -> String,
    val dialogState: ExerciseUnitDialogState
) {
    QUANTITY(
        title = R.string.quantity,
        description = R.string.quantity_description,
        value = -1,
        amountConverterString = { resources, amount ->
            resources.quantityString(R.plurals.times, amount, amount)
        },
        dialogState = ExerciseUnitDialogState.Quantity(1000)
    ),
    DISTANCE(
        title = R.string.distance,
        description = R.string.distance_description,
        value = R.string.kilometers_meteres,
        amountConverterString = { resources, amount ->
            if (amount < 1000) {
                resources.quantityString(R.plurals.meters, amount, amount)
            } else {
                val kilometers = amount / 1000f
                val str = if (amount % 1000 == 0) "${kilometers.toInt()}" else "$kilometers"
                resources.quantityString(R.plurals.kilometers, kilometers.toInt(), str)
            }
        },
        dialogState = ExerciseUnitDialogState.Distance(1000)
    ),
    TIME(
        title = R.string.time,
        description = R.string.time_description,
        value = R.string.minutes_seconds,
        amountConverterString = { resources, amount ->
            if (amount < 60) {
                resources.quantityString(R.plurals.seconds, amount, amount)
            } else {
                val minutes = amount / 60
                val seconds = amount % 60

                val minutesStr = resources.quantityString(R.plurals.minutes, minutes, minutes)

                if (seconds > 0) {
                    val secondsStr = resources.quantityString(R.plurals.seconds, seconds, seconds)
                    "$minutesStr $secondsStr"
                } else {
                    minutesStr
                }
            }
        },
        dialogState = ExerciseUnitDialogState.Time(60)
    );

    companion object {
        val unitListItemStates: List<ExerciseUnitListItemState>
            get() = values().mapIndexed { index, model ->
                ExerciseUnitListItemState(model, index == 0)
            }
    }

}