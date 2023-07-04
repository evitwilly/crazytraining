package ru.freeit.crazytraining.training.data.repository

import ru.freeit.crazytraining.core.extensions.default
import ru.freeit.crazytraining.training.data.database.TrainingDatabase
import ru.freeit.crazytraining.training.model.TrainingModel

class TrainingRepositoryImpl(private val trainingDatabase: TrainingDatabase) : TrainingRepository {

    override suspend fun saveTraining(training: TrainingModel) = default {
        if (training.id > 0) {
            trainingDatabase.update(training.database)
        } else {
            trainingDatabase.save(training.database)
        }
    }

}