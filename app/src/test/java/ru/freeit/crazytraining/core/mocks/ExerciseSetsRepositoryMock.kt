package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.training.repository.ExerciseSetsRepository

class ExerciseSetsRepositoryMock : ExerciseSetsRepository {

    override suspend fun exerciseSetsByDate(date: String): List<ExerciseSetModel> = emptyList()

    override suspend fun saveExerciseSet(model: ExerciseSetModel) {}

    override suspend fun removeExerciseSet(model: ExerciseSetModel) {}

    override suspend fun removeExerciseSetsByDate(date: String) {}

}