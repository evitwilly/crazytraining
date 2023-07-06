package ru.freeit.crazytraining.exercise.data.repository

import ru.freeit.crazytraining.core.extensions.default
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListRepositoryImpl(private val exerciseDatabase: ExerciseDatabase) : ExerciseListRepository {

    override suspend fun saveExercise(model: ExerciseModel) = default {
        if (model.id > 0) {
            exerciseDatabase.update(model.database)
        } else {
            exerciseDatabase.save(model.database)
        }
    }

    override suspend fun removeExercise(model: ExerciseModel) = default {
        if (model.id > 0) {
            exerciseDatabase.delete(model.database)
        }
    }

    override suspend fun exercises() = default {
        exerciseDatabase.items().map { it.model }
    }

}