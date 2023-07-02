package ru.freeit.crazytraining.statistics.repository

import ru.freeit.crazytraining.statistics.viewmodel_states.StatisticsDetailState

interface StatisticsRepository {
    suspend fun exercisesStatistics(): List<StatisticsDetailState>
}