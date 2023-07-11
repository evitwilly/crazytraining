package ru.freeit.crazytraining.training.data.repository

import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

interface ExerciseSetsRepository {
    suspend fun saveExerciseSet(model: ExerciseSetModel)
    suspend fun removeExerciseSet(model: ExerciseSetModel)
    suspend fun exerciseSetsByDate(date: String): List<ExerciseSetModel>
}