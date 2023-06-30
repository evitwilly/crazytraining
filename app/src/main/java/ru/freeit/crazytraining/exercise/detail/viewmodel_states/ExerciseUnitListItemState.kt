package ru.freeit.crazytraining.exercise.detail.viewmodel_states

import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel

class ExerciseUnitListItemState(val model: ExerciseUnitModel, val checked: Boolean) {
    fun withChangedChecked(checked: Boolean) = ExerciseUnitListItemState(model, checked)

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseUnitListItemState) return false

        return model == other.model && checked == other.checked
    }

    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + checked.hashCode()
        return result
    }
}