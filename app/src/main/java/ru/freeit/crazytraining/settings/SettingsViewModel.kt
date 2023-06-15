package ru.freeit.crazytraining.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayListState
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayState

class SettingsViewModel(private val repository: CheckedWeekdaysRepository) : BaseViewModel() {

    private val listState = MutableLiveData<WeekdayListState>()
    val state: LiveData<WeekdayListState> = listState

    init {
        val checkedWeekdays = repository.readCheckedWeekdays()
        listState.value = WeekdayListState(WeekdayModel.values().map { model -> WeekdayState(model, checkedWeekdays.contains(model)) })
    }

    fun changeWeekdayState(newState: WeekdayState) {
        listState.value = listState.value?.withStateChanged(newState)
        if (newState.checked) {
            repository.saveCheckedWeekday(newState.model)
        } else {
            repository.removeCheckedWeekday(newState.model)
        }
    }

}