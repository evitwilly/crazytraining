package ru.freeit.crazytraining.exercise.list.viewmodel_states

import ru.freeit.crazytraining.exercise.list.adapter.ExerciseEditButtonViewModel
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseDetailState(
    val exerciseModel: ExerciseModel,
    val editButtonViewModel: ExerciseEditButtonViewModel
) {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseDetailState) return false

        return exerciseModel == other.exerciseModel && editButtonViewModel == other.editButtonViewModel
    }

    override fun hashCode(): Int {
        var result = exerciseModel.hashCode()
        result = 31 * result + editButtonViewModel.hashCode()
        return result
    }

    override fun toString(): String {
        return "{ exercise_model -> $exerciseModel, editButtonViewModel -> $editButtonViewModel }"
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