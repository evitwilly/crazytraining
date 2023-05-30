package ru.freeit.crazytraining.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository

class TrainingViewModel(
    private val calendarRepository: CalendarRepository,
    private val checkedWeekdaysRepository: CheckedWeekdaysRepository
) : ViewModel() {

    private val _titleState = MutableLiveData<Int>()
    val titleState: LiveData<Int> = _titleState

    fun checkToday() {
        val isTodayTraining = checkedWeekdaysRepository.readCheckedWeekdays().map { it.calendarVariable }.contains(calendarRepository.weekday())
        if (isTodayTraining) {
            _titleState.value = R.string.training
        } else {
            _titleState.value = R.string.weekend
        }
    }

}