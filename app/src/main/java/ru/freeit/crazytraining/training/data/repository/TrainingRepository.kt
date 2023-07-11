package ru.freeit.crazytraining.training.data.repository

import ru.freeit.crazytraining.training.model.TrainingModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState

interface TrainingRepository {
    suspend fun saveTraining(training: TrainingModel)
    suspend fun removeTraining(training: TrainingModel)
    suspend fun trainingByDate(date: String): TrainingModel
    suspend fun exercisesWithSetsByTraining(trainingId: Int) : TrainingListState
}

