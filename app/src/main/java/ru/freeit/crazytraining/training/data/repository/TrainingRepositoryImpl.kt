package ru.freeit.crazytraining.training.data.repository

import ru.freeit.crazytraining.core.database.SQLiteSelection
import ru.freeit.crazytraining.core.extensions.default
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.data.repository.ExerciseActiveRepository
import ru.freeit.crazytraining.training.data.database.TrainingDatabase
import ru.freeit.crazytraining.training.data.database.TrainingTableDb
import ru.freeit.crazytraining.training.model.TrainingModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState

class TrainingRepositoryImpl(
    private val exerciseDatabase: ExerciseDatabase,
    private val exerciseSetDatabase: ExerciseSetDatabase,
    private val trainingDatabase: TrainingDatabase,
    private val activeRepository: ExerciseActiveRepository
) : TrainingRepository {

    override suspend fun saveTraining(training: TrainingModel) = default<Unit> {
        if (training.id > 0) {
            trainingDatabase.update(training.database)
        } else {
            trainingDatabase.save(training.database)
        }
    }

    override suspend fun removeTraining(training: TrainingModel) = default {
        exerciseSetDatabase.deleteByTraining(training.id)
        trainingDatabase.delete(training.database)
    }

    override suspend fun trainingByDate(date: String) = default {
        val trainingsByDate = trainingDatabase.items(SQLiteSelection().select(TrainingTableDb.column_date, date))
        trainingsByDate.firstOrNull()?.model ?: TrainingModel()
    }

    override suspend fun exercisesWithSetsByTraining(trainingId: Int) = default {
        val activeExerciseIds = activeRepository.activeExerciseIds()
        val exercises = exerciseDatabase.items().filter { activeExerciseIds.contains(it.id) }.map { it.model }
        val sets = exerciseSetDatabase.itemsByTrainingId(trainingId).map { it.model }

        val states = exercises.map { exercise ->
            TrainingDetailState(
                model = exercise,
                sets = sets.filter { set -> set.isThisExercise(exercise) }
            )
        }

        TrainingListState(states)
    }

}