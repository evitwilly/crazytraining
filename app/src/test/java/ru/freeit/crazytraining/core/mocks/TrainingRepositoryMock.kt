package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.training.data.repository.TrainingRepository
import ru.freeit.crazytraining.training.model.TrainingModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState

class TrainingRepositoryMock(params: List<TrainingModel> = emptyList()) : TrainingRepository {

    private val items = mutableListOf<TrainingModel>()

    init {
        items.addAll(params)
    }

    override suspend fun saveTraining(training: TrainingModel) {
        items.add(training)
    }

    override suspend fun trainingByDate(date: String): TrainingModel {
        return items.find { it.isThisDate(date) } ?: TrainingModel()
    }

    override suspend fun exercisesWithSetsByTraining(trainingId: Int) =
        TrainingListState(emptyList())

}