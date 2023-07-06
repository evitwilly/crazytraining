package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListRepositoryMock(params: List<ExerciseModel> = emptyList()) : ExerciseListRepository {

    private val items = mutableListOf<ExerciseModel>()

    init {
        items.addAll(params)
    }

    fun changeItems(newItems: List<ExerciseModel>) {
        items.clear()
        items.addAll(newItems)
    }

    override suspend fun saveExercise(model: ExerciseModel) {
        items.add(model)
    }

    override suspend fun removeExercise(model: ExerciseModel) {
        items.remove(model)
    }

    override suspend fun exercises(): List<ExerciseModel> = items

}