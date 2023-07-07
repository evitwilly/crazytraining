package ru.freeit.crazytraining.training.viewmodel_states

import ru.freeit.crazytraining.R

sealed interface TrainingActiveState {
    object Training : TrainingActiveState {
        const val buttonTitle = R.string.finish_training
    }
    object Weekend : TrainingActiveState
    object Finished : TrainingActiveState {
        const val buttonTitle = R.string.resume_training
    }
}