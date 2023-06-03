package ru.freeit.crazytraining.exercise.repository

import ru.freeit.crazytraining.exercise.model.ExerciseModel

interface ExerciseListRepository {
    fun saveExercise(model: ExerciseModel)
    fun exercises() : List<ExerciseModel>
}