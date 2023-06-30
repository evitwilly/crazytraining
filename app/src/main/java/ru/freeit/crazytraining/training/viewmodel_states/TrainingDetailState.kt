package ru.freeit.crazytraining.training.viewmodel_states

import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

class TrainingDetailStateListeners(
    val addListener: (ExerciseModel) -> Unit,
    val removeListener: (ExerciseSetModel) -> Unit,
    val plusListener: (ExerciseSetModel) -> Unit,
    val minusListener: (ExerciseSetModel) -> Unit
)

class TrainingDetailState(
    val model: ExerciseModel,
    private val sets: List<ExerciseSetModel>
) {

    val model_with_total_amount: ExerciseSetModel
        get() = ExerciseSetModel(amount = sets.sumOf { it.amount }, unit = model.unit)

    val sorted_sets_by_number: List<Pair<ExerciseSetModel, Int>>
        get() {
            val countedNumberOfAmountsWithLastIndexOfItem = linkedMapOf<Int, Pair<Int, Int>>()

            sets.forEachIndexed { index, set ->
                val amount = set.amount
                if (countedNumberOfAmountsWithLastIndexOfItem.containsKey(amount)) {
                    val previousValue = countedNumberOfAmountsWithLastIndexOfItem[amount]?.second ?: 1
                    countedNumberOfAmountsWithLastIndexOfItem[amount] = index to previousValue + 1
                } else {
                    countedNumberOfAmountsWithLastIndexOfItem[amount] = index to 1
                }
            }

            val result = mutableListOf<Pair<ExerciseSetModel, Int>>()

            countedNumberOfAmountsWithLastIndexOfItem.entries.forEach { (_, value) ->
                result.add(sets[value.first] to value.second)
            }

            return result
        }

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