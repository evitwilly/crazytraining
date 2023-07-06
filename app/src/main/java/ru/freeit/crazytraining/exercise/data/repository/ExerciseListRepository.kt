package ru.freeit.crazytraining.exercise.data.repository

import ru.freeit.crazytraining.exercise.model.ExerciseModel

interface ExerciseListRepository {
    suspend fun saveExercise(model: ExerciseModel)
    suspend fun removeExercise(model: ExerciseModel)
    suspend fun exercises() : List<ExerciseModel>
}