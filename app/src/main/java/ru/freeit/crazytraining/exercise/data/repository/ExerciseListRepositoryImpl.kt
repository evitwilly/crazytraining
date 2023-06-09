package ru.freeit.crazytraining.exercise.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class ExerciseListRepositoryImpl(
    private val exerciseDatabase: ExerciseDatabase,
    private val exerciseSetDatabase: ExerciseSetDatabase
) : ExerciseListRepository {

    override suspend fun saveExercise(model: ExerciseModel) = withContext(Dispatchers.Default) {
        exerciseDatabase.save(model.database)
    }

    override suspend fun exercises() = withContext(Dispatchers.Default) {
        exerciseDatabase.items().map { it.model }
    }

    override suspend fun saveExerciseSet(model: ExerciseSetModel) = withContext(Dispatchers.Default) {
        exerciseSetDatabase.save(model.database)
    }

    override suspend fun exercisesWithSets() = withContext(Dispatchers.Default) {
        exerciseDatabase.items().map { database ->
            val sets = exerciseSetDatabase.itemsByExerciseId(database.id).map { it.model }
            database.model.withSets(sets)
        }
    }

}