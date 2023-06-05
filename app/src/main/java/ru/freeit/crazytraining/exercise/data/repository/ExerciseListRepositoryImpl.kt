package ru.freeit.crazytraining.exercise.data.repository

import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListRepositoryImpl : ExerciseListRepository {
    override fun saveExercise(model: ExerciseModel) {}
    override fun exercises(): List<ExerciseModel> = emptyList()
}