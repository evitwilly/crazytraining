package ru.freeit.crazytraining.settings

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.core.viewmodel.SavedInstanceState
import ru.freeit.crazytraining.core.viewmodel.SingleLiveEvent
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayListState
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayState
import ru.freeit.crazytraining.training.repository.ExerciseSetsRepository

class SettingsViewModel(
    savedState: SavedInstanceState,
    private val weekdaysRepository: CheckedWeekdaysRepository,
    private val calendarRepository: CalendarRepository,
    private val exerciseSetsRepository: ExerciseSetsRepository
) : BaseViewModel() {

    private val listState = MutableLiveData<WeekdayListState>()
    val state: LiveData<WeekdayListState> = listState

    private val _acceptDialogState = SingleLiveEvent<Boolean>()
    val acceptDialogState: LiveData<Boolean> = _acceptDialogState

    private var cachedWeekdayState: WeekdayState? = savedState.parcelable(cache_weekday_state_key, WeekdayState::class.java)

    private var exerciseSetsInToday = mutableListOf<ExerciseSetModel>()

    init {
        val checkedWeekdays = weekdaysRepository.readCheckedWeekdays()
        listState.value = WeekdayListState(WeekdayModel.values().map { model -> WeekdayState(model, checkedWeekdays.contains(model)) })

        uiScope.launch {
            exerciseSetsInToday.clear()
            exerciseSetsInToday.addAll(exerciseSetsRepository.exerciseSetsByDate(calendarRepository.dateStringFrom()))
        }
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        bundle.putParcelable(cache_weekday_state_key, cachedWeekdayState)
    }

    fun dialogOkClick() {
        val newState = cachedWeekdayState ?: return

        uiScope.launch {

            exerciseSetsRepository.removeExerciseSetsByDate(calendarRepository.dateStringFrom())

            exerciseSetsInToday.clear()

            changeWeekdayState(newState)
        }
    }

    fun changeWeekdayState(newState: WeekdayState) {
        if (newState.model.calendarVariable == calendarRepository.weekday() && exerciseSetsInToday.isNotEmpty()) {
            cachedWeekdayState = newState
            _acceptDialogState.value = true
            listState.value = listState.value
            return
        }

        listState.value = listState.value?.withStateChanged(newState)
        if (newState.checked) {
            weekdaysRepository.saveCheckedWeekday(newState.model)
        } else {
            weekdaysRepository.removeCheckedWeekday(newState.model)
        }
    }

    private companion object {
        const val cache_weekday_state_key = "cache_weekday_state_key"
    }

}