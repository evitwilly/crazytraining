package ru.freeit.crazytraining.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingTextState

class TrainingViewModel(
    private val exerciseListRepository: ExerciseListRepository,
    private val calendarRepository: CalendarRepository,
    private val checkedWeekdaysRepository: CheckedWeekdaysRepository
) : BaseViewModel() {

    private val _textState = MutableLiveData<TrainingTextState>()
    val textState: LiveData<TrainingTextState> = _textState

    private val _trainingState = MutableLiveData<TrainingListState>()
    val trainingState: LiveData<TrainingListState> = _trainingState

    fun updateState() {
        val isTodayTraining = checkedWeekdaysRepository.readCheckedWeekdays().map { it.calendarVariable }.contains(calendarRepository.weekday())
        _textState.value = TrainingTextState(
            if (isTodayTraining) R.string.training else R.string.weekend,
            calendarRepository.weekdayMonthYearDateString()
        )
        uiScope.launch {
            _trainingState.value = exerciseListRepository.exercisesWithSets()
        }
    }

}