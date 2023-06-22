package ru.freeit.crazytraining.training.viewmodel_states

sealed interface TrainingWeekendState {
    object Training : TrainingWeekendState
    object Weekend : TrainingWeekendState
}