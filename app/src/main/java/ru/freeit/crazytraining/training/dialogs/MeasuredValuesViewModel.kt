package ru.freeit.crazytraining.training.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.freeit.crazytraining.core.viewmodel.SingleLiveEvent
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.training.dialogs.viewmodel_states.MeasuredValuesState

class MeasuredValuesViewModel(argument: ExerciseMeasuredValueModel) : ViewModel() {

    private val _measuredValuesState = MutableLiveData<MeasuredValuesState>()
    val measuredValuesState: LiveData<MeasuredValuesState> = _measuredValuesState
    
    private val _amountState = SingleLiveEvent<Int>()
    val amountState: LiveData<Int> = _amountState

    private var cachedAmount: Int = 0

    init {
        _measuredValuesState.value = argument.measuredValuesState
    }

    fun cacheAmount(amount: Int) {
        cachedAmount = amount
    }

    fun apply() {
        if (cachedAmount > 0) {
            _amountState.value = cachedAmount
        }
    }

}