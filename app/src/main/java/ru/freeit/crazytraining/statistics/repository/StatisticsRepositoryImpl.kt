package ru.freeit.crazytraining.statistics.repository

import ru.freeit.crazytraining.core.ResourcesProvider
import ru.freeit.crazytraining.core.extensions.default
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.statistics.viewmodel_states.StatisticsDetailState

class StatisticsRepositoryImpl(
    private val exerciseDatabase: ExerciseDatabase,
    private val exerciseSetDatabase: ExerciseSetDatabase,
    resourcesProvider: ResourcesProvider
) : StatisticsRepository {

    private val markDefinitionStrategies = listOf(
        StatisticsMarkDefinitionStrategy.TotalSets(resourcesProvider),
        StatisticsMarkDefinitionStrategy.TotalAmount(resourcesProvider)
    )

    override suspend fun exercisesStatistics() = default {
        val exercises = exerciseDatabase.items().map { it.model }
        val sets = exerciseSetDatabase.items().map { it.model }

        exercises.map { model ->
            val currentSets = sets.filter { it.isThisExercise(model) }
            StatisticsDetailState(
                model = model,
                marks = markDefinitionStrategies.map { strategy ->
                    strategy.mark(
                        model,
                        currentSets
                    )
                }
            )
        }
    }

}