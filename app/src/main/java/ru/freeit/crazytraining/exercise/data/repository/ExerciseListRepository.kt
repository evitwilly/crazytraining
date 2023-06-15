package ru.freeit.crazytraining.exercise.data.repository

import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

interface ExerciseListRepository {
    suspend fun saveExercise(model: ExerciseModel)
    suspend fun removeExercise(model: ExerciseModel)
    suspend fun exercises() : List<ExerciseModel>
    suspend fun saveExerciseSet(model: ExerciseSetModel)
    suspend fun exercisesWithSets() : List<ExerciseModel>
}