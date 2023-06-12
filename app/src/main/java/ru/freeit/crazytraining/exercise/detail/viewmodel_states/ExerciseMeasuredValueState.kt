package ru.freeit.crazytraining.exercise.detail.viewmodel_states

import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel

class ExerciseMeasuredValueState(val model: ExerciseMeasuredValueModel, val checked: Boolean) {
    fun withChangedChecked(checked: Boolean) = ExerciseMeasuredValueState(model, checked)

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseMeasuredValueState) return false

        return model == other.model && checked == other.checked
    }

    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + checked.hashCode()
        return result
    }
}