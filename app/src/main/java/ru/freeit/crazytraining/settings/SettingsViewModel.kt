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
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayListState
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayState
import ru.freeit.crazytraining.training.data.repository.TrainingRepository
import ru.freeit.crazytraining.training.model.TrainingModel

class SettingsViewModel(
    savedState: SavedInstanceState,
    private val weekdaysRepository: CheckedWeekdaysRepository,
    private val calendarRepository: CalendarRepository,
    private val trainingRepository: TrainingRepository
) : BaseViewModel() {

    private val listState = MutableLiveData<WeekdayListState>()
    val state: LiveData<WeekdayListState> = listState

    private val _acceptDialogState = SingleLiveEvent<Boolean>()
    val acceptDialogState: LiveData<Boolean> = _acceptDialogState

    private var cachedWeekdayState: WeekdayState? = savedState.parcelable(cached_weekday_state_key, WeekdayState::class.java)
    private var cachedTrainingModel: TrainingModel? = savedState.parcelable(cached_training_model_key, TrainingModel::class.java)

    init {
        val checkedWeekdays = weekdaysRepository.readCheckedWeekdays()
        listState.value = WeekdayListState(WeekdayModel.values().map { model -> WeekdayState(model, checkedWeekdays.contains(model)) })
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        bundle.putParcelable(cached_weekday_state_key, cachedWeekdayState)
        bundle.putParcelable(cached_training_model_key, cachedTrainingModel)
    }

    fun dialogOkClick() {
        val newState = cachedWeekdayState ?: return
        val trainingModel = cachedTrainingModel ?: return

        uiScope.launch {
            trainingRepository.removeTraining(trainingModel)
            changeWeekdayState(newState)
        }
    }

    fun changeWeekdayState(newState: WeekdayState) = uiScope.launch {
        val todayTraining = trainingRepository.trainingByDate(calendarRepository.dateStringFrom())
        val isTodayWeekday = newState.model.calendarVariable == calendarRepository.weekday()

        if (isTodayWeekday && todayTraining.hasNotFinished) {
            cachedWeekdayState = newState
            cachedTrainingModel = todayTraining
            _acceptDialogState.value = true
            listState.value = listState.value
        } else {

            if (newState.checked) {
                weekdaysRepository.saveCheckedWeekday(newState.model)
            } else {
                weekdaysRepository.removeCheckedWeekday(newState.model)
            }

            listState.value = listState.value?.withStateChanged(newState)
        }

    }

    private companion object {
        const val cached_weekday_state_key = "cached_weekday_state_key"
        const val cached_training_model_key = "cached_training_model_key"
    }

}