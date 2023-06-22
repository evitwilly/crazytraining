package ru.freeit.crazytraining.training.repository

import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

interface ExerciseSetsRepository {
    suspend fun saveExerciseSet(model: ExerciseSetModel)
    suspend fun removeExerciseSet(model: ExerciseSetModel)
    suspend fun exerciseSetsByDate(date: String): List<ExerciseSetModel>
    suspend fun removeExerciseSetsByDate(date: String)
}