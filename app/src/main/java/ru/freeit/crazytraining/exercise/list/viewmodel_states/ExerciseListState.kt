package ru.freeit.crazytraining.exercise.list.viewmodel_states

import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.exercise.list.adapter.ExerciseViewHolder
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseDetailState(
    val exerciseModel: ExerciseModel,
    private val active: Boolean
) {

    val toggled_active: Boolean
        get() = !active

    fun bindStatus(view: ExerciseViewHolder.StatusButton) {
        view.hasActive = active
        view.setText(if (active) R.string.active else R.string.inactive)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseDetailState) return false

        return exerciseModel == other.exerciseModel && active == other.active
    }

    override fun hashCode(): Int {
        var result = exerciseModel.hashCode()
        result = 31 * result + active.hashCode()
        return result
    }

    override fun toString(): String {
        return "{ exercise_model -> $exerciseModel, active -> $active }"
    }

}

class ExerciseListState(val items: List<ExerciseDetailState>) {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseListState) return false

        return items == other.items
    }

    override fun hashCode(): Int = items.hashCode()

    override fun toString(): String {
        return "[${items.joinToString { it.toString() }}]"
    }

}