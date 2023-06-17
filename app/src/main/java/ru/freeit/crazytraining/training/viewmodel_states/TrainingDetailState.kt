package ru.freeit.crazytraining.training.viewmodel_states

import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class TrainingDetailState(
    val model: ExerciseModel,
    private val sets: List<ExerciseSetModel>
) {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is TrainingDetailState) return false

        return model == other.model && sets == other.sets
    }

    override fun hashCode(): Int {
        var result = model.hashCode()
        result = 31 * result + sets.hashCode()
        return result
    }

}