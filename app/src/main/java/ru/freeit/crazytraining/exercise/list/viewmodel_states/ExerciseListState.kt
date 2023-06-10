package ru.freeit.crazytraining.exercise.list.viewmodel_states

import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.list.adapter.ExerciseListAdapter

class ExerciseListState(private val items: List<ExerciseModel>) {
    val adapter: ExerciseListAdapter
        get() = ExerciseListAdapter(items)

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseListState) return false

        return items == other.items
    }

    override fun hashCode(): Int = items.hashCode()
}