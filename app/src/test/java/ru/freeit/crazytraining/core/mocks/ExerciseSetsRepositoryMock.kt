package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.training.data.repository.ExerciseSetsRepository

class ExerciseSetsRepositoryMock : ExerciseSetsRepository {

    val data = mutableListOf<ExerciseSetModel>()

    override suspend fun exerciseSetsByDate(date: String): List<ExerciseSetModel> = data

    override suspend fun saveExerciseSet(model: ExerciseSetModel) {
        data.add(model)
    }

    override suspend fun removeExerciseSet(model: ExerciseSetModel) {
        data.remove(model)
    }

    override suspend fun removeExerciseSetsByDate(date: String) {
        data.clear()
    }

}