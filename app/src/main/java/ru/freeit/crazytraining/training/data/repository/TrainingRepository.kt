package ru.freeit.crazytraining.training.data.repository

import ru.freeit.crazytraining.training.model.TrainingModel

interface TrainingRepository {
    suspend fun saveTraining(training: TrainingModel)
}

