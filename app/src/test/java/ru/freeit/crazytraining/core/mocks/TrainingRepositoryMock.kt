package ru.freeit.crazytraining.core.mocks

import ru.freeit.crazytraining.training.data.repository.TrainingRepository
import ru.freeit.crazytraining.training.model.TrainingModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState

class TrainingRepositoryMock(
    trainings: List<TrainingModel> = emptyList(),
    private val listStates: List<TrainingDetailState> = emptyList()
) : TrainingRepository {

    val items = mutableListOf<TrainingModel>()

    init {
        items.addAll(trainings)
    }

    override suspend fun saveTraining(training: TrainingModel) {
        items.add(training.copy(id = items.size + 1))
    }

    override suspend fun removeTraining(training: TrainingModel) {
        items.remove(training)
    }

    override suspend fun trainingByDate(date: String): TrainingModel {
        return items.find { it.isThisDate(date) } ?: TrainingModel()
    }

    override suspend fun exercisesWithSetsByTraining(trainingId: Int) =
        TrainingListState(listStates)

}