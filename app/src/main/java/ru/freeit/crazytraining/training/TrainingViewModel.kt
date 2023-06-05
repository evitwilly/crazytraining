package ru.freeit.crazytraining.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.BaseViewModel
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.training.viewmodel_states.ExerciseListState

class TrainingViewModel(
    private val exerciseListRepository: ExerciseListRepository,
    private val calendarRepository: CalendarRepository,
    private val checkedWeekdaysRepository: CheckedWeekdaysRepository
) : BaseViewModel() {

    private val _titleState = MutableLiveData<Int>()
    val titleState: LiveData<Int> = _titleState

    private val _dateState = MutableLiveData<String>()
    val dateState: LiveData<String> = _dateState

    private val _exerciseListState = MutableLiveData<ExerciseListState>()
    val exerciseListState: LiveData<ExerciseListState> = _exerciseListState

    fun updateState() {
        val isTodayTraining = checkedWeekdaysRepository.readCheckedWeekdays().map { it.calendarVariable }.contains(calendarRepository.weekday())
        if (isTodayTraining) {
            _titleState.value = R.string.training
        } else {
            _titleState.value = R.string.weekend
        }
        _dateState.value = calendarRepository.weekdayMonthYearDateString()
        uiScope.launch {
            _exerciseListState.value = ExerciseListState(exerciseListRepository.exercises())
        }
    }

}