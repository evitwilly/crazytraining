package ru.freeit.crazytraining.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayListState
import ru.freeit.crazytraining.settings.viewmodel_states.WeekdayState

class SettingsViewModel : ViewModel() {

    private val listState = MutableLiveData<WeekdayListState>()
    val state: LiveData<WeekdayListState> = listState

    init {
        listState.value = WeekdayListState(WeekdayModel.values().map { WeekdayState(it, false) })
    }

    fun changeWeekdayState(newState: WeekdayState) {
        listState.value = listState.value?.withStateChanged(newState)
    }

}