package ru.freeit.crazytraining.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.statistics.repository.StatisticsRepository
import ru.freeit.crazytraining.statistics.viewmodel_states.StatisticsDetailState

class StatisticsViewModel(private val repository: StatisticsRepository) : BaseViewModel() {

    private val _state = MutableLiveData<List<StatisticsDetailState>>()
    val state: LiveData<List<StatisticsDetailState>> = _state

    fun onStart() = uiScope.launch {
        _state.value = repository.exercisesStatistics()
    }

}