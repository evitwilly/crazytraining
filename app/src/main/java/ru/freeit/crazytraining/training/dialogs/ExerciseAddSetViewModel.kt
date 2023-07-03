package ru.freeit.crazytraining.training.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.freeit.crazytraining.core.viewmodel.SingleLiveEvent
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.training.dialogs.viewmodel_states.ExerciseUnitDialogState

class ExerciseAddSetViewModel(argument: ExerciseUnitModel) : ViewModel() {

    private val _exerciseUnitState = MutableLiveData<ExerciseUnitDialogState>()
    val exerciseUnitState: LiveData<ExerciseUnitDialogState> = _exerciseUnitState
    
    private val _amountState = SingleLiveEvent<Int>()
    val amountState: LiveData<Int> = _amountState

    private var cachedAmount: Int = 0

    init {
        _exerciseUnitState.value = argument.dialogState
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