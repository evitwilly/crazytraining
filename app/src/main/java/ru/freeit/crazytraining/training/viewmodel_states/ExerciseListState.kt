package ru.freeit.crazytraining.training.viewmodel_states

import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.training.adapter.ExerciseListAdapter

class ExerciseListState(private val items: List<ExerciseModel>) {
    val adapter: ExerciseListAdapter
        get() = ExerciseListAdapter(items)
}