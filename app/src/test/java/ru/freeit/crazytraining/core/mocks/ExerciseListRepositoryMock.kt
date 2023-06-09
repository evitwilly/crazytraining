package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class ExerciseListRepositoryMock(params: List<ExerciseModel> = emptyList()) : ExerciseListRepository {
    private val items = mutableListOf<ExerciseModel>()

    init {
        items.addAll(params)
    }

    override suspend fun saveExercise(model: ExerciseModel) {
        items.add(model)
    }

    override suspend fun exercises(): List<ExerciseModel> = items

    override suspend fun saveExerciseSet(model: ExerciseSetModel) {}

    override suspend fun exercisesWithSets(): List<ExerciseModel> = emptyList()

}