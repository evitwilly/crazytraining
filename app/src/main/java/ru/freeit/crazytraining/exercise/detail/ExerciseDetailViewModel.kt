package ru.freeit.crazytraining.exercise.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.core.viewmodel.SingleLiveEvent
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseSettingsState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseUnitListState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseUnitListItemState
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseDetailViewModel(
    private val argument: ExerciseModel?,
    private val listRepository: ExerciseListRepository
) : BaseViewModel() {

    private val _exerciseSettingsState = MutableLiveData<ExerciseSettingsState>()
    val exerciseSettingsState: LiveData<ExerciseSettingsState> = _exerciseSettingsState

    private val _titleError = SingleLiveEvent<Int>()
    val titleError: LiveData<Int> = _titleError

    init {
        _exerciseSettingsState.value = argument?.exerciseSettingsState ?: ExerciseSettingsState(
            unitListState = ExerciseUnitListState(ExerciseUnitModel.unitListItemStates)
        )
    }

    fun changeTitle(title: String) {
        _exerciseSettingsState.value = _exerciseSettingsState.value?.withChangedTitle(title)
    }

    fun checkMeasuredState(newState: ExerciseUnitListItemState) {
        val measuredState = _exerciseSettingsState.value?.unitListState ?: return
        val newMeasuredState = measuredState.withCheckedState(newState)
        _exerciseSettingsState.value = _exerciseSettingsState.value?.withChangedMeasuredState(newMeasuredState)
    }

    fun apply() {
        val state = _exerciseSettingsState.value ?: return
        if (!state.is_valid) {
            _titleError.value = R.string.the_field_is_empty
        } else {
            uiScope.launch {
                listRepository.saveExercise(state.model(argument?.id ?: 0))
                back()
            }
        }
    }

}