package ru.freeit.crazytraining.exercise.detail.viewmodel_states

import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseSettingsState(
    private val title: String = "",
    val unitListState: ExerciseUnitListState
) {
    val is_valid: Boolean
        get() = title.isNotBlank()

    fun model(id: Int): ExerciseModel {
        return ExerciseModel(
            title = title,
            unit = unitListState.checkedUnitModel,
            id = id
        )
    }

    fun withChangedTitle(title: String) = ExerciseSettingsState(title, unitListState)
    fun withChangedMeasuredState(measuredState: ExerciseUnitListState) = ExerciseSettingsState(title, measuredState)

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseSettingsState) return false
        return title == other.title && unitListState == other.unitListState
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + unitListState.hashCode()
        return result
    }

}