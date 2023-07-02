package ru.freeit.crazytraining.statistics.repository

import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.ResourcesProvider
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

interface StatisticsMarkDefinitionStrategy {
    fun mark(model: ExerciseModel, sets: List<ExerciseSetModel>): String

    class TotalSets(private val resourcesProvider: ResourcesProvider) : StatisticsMarkDefinitionStrategy {
        override fun mark(model: ExerciseModel, sets: List<ExerciseSetModel>): String {
            return resourcesProvider.string(R.string.total_sets, sets.size)
        }
    }

    class TotalAmount(private val resourcesProvider: ResourcesProvider): StatisticsMarkDefinitionStrategy {
        override fun mark(model: ExerciseModel, sets: List<ExerciseSetModel>): String {
            val totalAmount = sets.sumOf { it.amount }
            val totalAmountString = ExerciseSetModel(amount = totalAmount).amountString(resourcesProvider)
            return resourcesProvider.string(R.string.total_amount, totalAmountString)
        }
    }

}