package ru.freeit.crazytraining.training.data.repository

import ru.freeit.crazytraining.core.extensions.default
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class ExerciseSetsRepositoryImpl(private val exerciseSetDatabase: ExerciseSetDatabase) :
    ExerciseSetsRepository {

    override suspend fun saveExerciseSet(model: ExerciseSetModel) = default<Unit> {
        exerciseSetDatabase.save(model.database)
    }

    override suspend fun removeExerciseSet(model: ExerciseSetModel) = default {
        exerciseSetDatabase.delete(model.database)
    }

    override suspend fun exerciseSetsByDate(date: String) = default {
        exerciseSetDatabase.itemsByDate(date).map { it.model }
    }

    override suspend fun removeExerciseSetsByDate(date: String) = default {
        exerciseSetDatabase.deleteByDate(date)
    }

}