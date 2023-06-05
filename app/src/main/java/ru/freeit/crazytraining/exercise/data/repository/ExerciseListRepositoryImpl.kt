package ru.freeit.crazytraining.exercise.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListRepositoryImpl(private val database: ExerciseDatabase) : ExerciseListRepository {

    override suspend fun saveExercise(model: ExerciseModel) = withContext(Dispatchers.Default) {
        database.save(model.database)
    }

    override suspend fun exercises() = withContext(Dispatchers.Default) {
        database.items().map { it.model }
    }

}