package ru.freeit.crazytraining.exercise.list.viewmodel_states

import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListState(val items: List<ExerciseModel>) {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseListState) return false

        return items == other.items
    }

    override fun hashCode(): Int = items.hashCode()
}