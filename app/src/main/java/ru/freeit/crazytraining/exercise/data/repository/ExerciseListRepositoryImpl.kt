package ru.freeit.crazytraining.exercise.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState

class ExerciseListRepositoryImpl(
    private val exerciseDatabase: ExerciseDatabase,
    private val exerciseSetDatabase: ExerciseSetDatabase
) : ExerciseListRepository {

    override suspend fun saveExercise(model: ExerciseModel) = withContext(Dispatchers.Default) {
        if (model.id > 0) {
            exerciseDatabase.update(model.database)
        } else {
            exerciseDatabase.save(model.database)
        }
    }

    override suspend fun removeExercise(model: ExerciseModel) = withContext(Dispatchers.Default) {
        if (model.id > 0) {
            exerciseDatabase.delete(model.database)
        }
    }

    override suspend fun exercises() = withContext(Dispatchers.Default) {
        exerciseDatabase.items().map { it.model }
    }

    override suspend fun saveExerciseSet(model: ExerciseSetModel) = withContext(Dispatchers.Default) {
        exerciseSetDatabase.save(model.database)
    }

    override suspend fun removeExerciseSet(model: ExerciseSetModel) = withContext(Dispatchers.Default) {
        exerciseSetDatabase.delete(model.database)
    }

    override suspend fun exerciseSetsByDate(date: String) = withContext(Dispatchers.Default) {
        exerciseSetDatabase.itemsByDate(date).map { it.model() }
    }

    override suspend fun removeExerciseSetsByDate(date: String) {
        exerciseSetDatabase.deleteByDate(date)
    }

    override suspend fun exercisesWithSetsByDate(date: String) = withContext(Dispatchers.Default) {
        val detailStates = exerciseDatabase.items().map { database ->
            val sets = exerciseSetDatabase.itemsByExerciseId(database.id, date).map { it.model() }
            TrainingDetailState(database.model, sets)
        }
        TrainingListState(detailStates)
    }

}