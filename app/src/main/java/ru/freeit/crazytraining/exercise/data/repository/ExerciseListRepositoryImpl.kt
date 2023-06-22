package ru.freeit.crazytraining.exercise.data.repository

import ru.freeit.crazytraining.core.extensions.default
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState

class ExerciseListRepositoryImpl(
    private val exerciseDatabase: ExerciseDatabase,
    private val exerciseSetDatabase: ExerciseSetDatabase
) : ExerciseListRepository {

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

    override suspend fun exercisesWithSetsByDate(date: String) = default {
        val exercises = exerciseDatabase.items().map { it.model }
        val sets = exerciseSetDatabase.itemsByDate(date).map { it.model }

        val states = exercises.map { exercise ->
            TrainingDetailState(
                model = exercise,
                sets = sets.filter { set -> set.isThisExercise(exercise) }
            )
        }

        TrainingListState(states)
    }

}