package ru.freeit.crazytraining.exercise.data.repository

import ru.freeit.crazytraining.core.extensions.default
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseDetailState
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseListState
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListRepositoryImpl(
    private val exerciseDatabase: ExerciseDatabase,
    private val activeRepository: ExerciseActiveRepository
) : ExerciseListRepository {

    override suspend fun saveExercise(model: ExerciseModel) = default {
        if (model.id > 0) {
            exerciseDatabase.update(model.database)
        } else {
            val exerciseId = exerciseDatabase.save(model.database)
            activeRepository.checkActive(exerciseId.toInt(), true)
        }
    }

    override suspend fun removeExercise(model: ExerciseModel) = default {
        if (model.id > 0) {
            exerciseDatabase.delete(model.database)
        }
    }

    override suspend fun exercises() = default {
        val databaseExercises = exerciseDatabase.items()
        val activeExerciseIds = activeRepository.activeExerciseIds()
        ExerciseListState(databaseExercises.map { ExerciseDetailState(it.model, activeExerciseIds.contains(it.id)) })
    }

}