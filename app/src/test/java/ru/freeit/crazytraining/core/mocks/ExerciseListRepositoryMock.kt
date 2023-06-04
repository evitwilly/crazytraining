package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.repository.ExerciseListRepository

class ExerciseListRepositoryMock : ExerciseListRepository {
    override fun saveExercise(model: ExerciseModel) {}
    override fun exercises(): List<ExerciseModel> = emptyList()
}