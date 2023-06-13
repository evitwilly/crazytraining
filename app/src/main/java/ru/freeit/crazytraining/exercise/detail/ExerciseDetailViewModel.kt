package ru.freeit.crazytraining.exercise.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.BaseViewModel
import ru.freeit.crazytraining.core.viewmodel.SingleLiveEvent
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.detail.repository.ExerciseResourcesRepository
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.AddingExerciseState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseMeasuredValueListState
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseMeasuredValueState

class ExerciseDetailViewModel(
    private val listRepository: ExerciseListRepository,
    resourcesRepository: ExerciseResourcesRepository
) : BaseViewModel() {

    private val _addingExerciseState = MutableLiveData<AddingExerciseState>()
    val addingExerciseState: LiveData<AddingExerciseState> = _addingExerciseState

    private val _titleError = SingleLiveEvent<Int>()
    val titleError: LiveData<Int> = _titleError

    val checked_icon_fragment_arg: Int
        get() = addingExerciseState.value?.icon ?: 0

    val checked_color_fragment_arg: Int
        get() = addingExerciseState.value?.color ?: 0

    init {
        _addingExerciseState.value = AddingExerciseState(
            icon = resourcesRepository.icons().first(),
            color = resourcesRepository.colors().first(),
            measuredState = ExerciseMeasuredValueListState(ExerciseMeasuredValueModel.measuredStates)
        )
    }

    fun changeTitle(title: String) {
        _addingExerciseState.value = _addingExerciseState.value?.withChangedTitle(title)
    }

    fun checkColor(color: Int) {
        _addingExerciseState.value = _addingExerciseState.value?.withChangedColor(color)
    }

    fun checkIcon(icon: Int) {
        _addingExerciseState.value = _addingExerciseState.value?.withChangedIcon(icon)
    }

    fun checkMeasuredState(newState: ExerciseMeasuredValueState) {
        val measuredState = _addingExerciseState.value?.measuredState ?: return
        val newMeasuredState = measuredState.withCheckedState(newState)
        _addingExerciseState.value = _addingExerciseState.value?.withChangedMeasuredState(newMeasuredState)
    }

    fun apply() {
        val state = _addingExerciseState.value ?: return
        if (!state.is_valid) {
            _titleError.value = R.string.the_field_is_empty
        } else {
            uiScope.launch {
                listRepository.saveExercise(state.model)
                back()
            }
        }
    }

}