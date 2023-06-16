package ru.freeit.crazytraining.exercise.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.core.viewmodel.SingleLiveEvent
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.detail.repository.ExerciseResourcesRepository
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseSettingsState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseMeasuredValueListState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseMeasuredValueState
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseDetailViewModel(
    private val argument: ExerciseModel?,
    private val listRepository: ExerciseListRepository,
    resourcesRepository: ExerciseResourcesRepository,
) : BaseViewModel() {

    private val _exerciseSettingsState = MutableLiveData<ExerciseSettingsState>()
    val exerciseSettingsState: LiveData<ExerciseSettingsState> = _exerciseSettingsState

    private val _titleError = SingleLiveEvent<Int>()
    val titleError: LiveData<Int> = _titleError

    val checked_icon_fragment_arg: Int
        get() = exerciseSettingsState.value?.icon ?: 0

    val checked_color_fragment_arg: Int
        get() = exerciseSettingsState.value?.color ?: 0

    init {
        _exerciseSettingsState.value = argument?.exerciseSettingsState ?: ExerciseSettingsState(
            icon = resourcesRepository.icons().first(),
            color = resourcesRepository.colors().first(),
            measuredState = ExerciseMeasuredValueListState(ExerciseMeasuredValueModel.measuredStates)
        )
    }

    fun changeTitle(title: String) {
        _exerciseSettingsState.value = _exerciseSettingsState.value?.withChangedTitle(title)
    }

    fun checkColor(color: Int) {
        _exerciseSettingsState.value = _exerciseSettingsState.value?.withChangedColor(color)
    }

    fun checkIcon(icon: Int) {
        _exerciseSettingsState.value = _exerciseSettingsState.value?.withChangedIcon(icon)
    }

    fun checkMeasuredState(newState: ExerciseMeasuredValueState) {
        val measuredState = _exerciseSettingsState.value?.measuredState ?: return
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