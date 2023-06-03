package ru.freeit.crazytraining.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.freeit.crazytraining.core.navigation.BaseViewModel
import ru.freeit.crazytraining.exercise.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.exercise.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.repository.ExerciseResourcesRepository
import ru.freeit.crazytraining.exercise.viewmodel_states.AddingExerciseState
import ru.freeit.crazytraining.exercise.viewmodel_states.ExerciseMeasuredValueListState
import ru.freeit.crazytraining.exercise.viewmodel_states.ExerciseMeasuredValueState
import ru.freeit.crazytraining.exercise.viewmodel_states.SettingsIconState

class ExerciseViewModel(
    private val listRepository: ExerciseListRepository,
    resourcesRepository: ExerciseResourcesRepository
) : BaseViewModel() {

    private val _addingExerciseState = MutableLiveData<AddingExerciseState>()
    val addingExerciseState: LiveData<AddingExerciseState> = _addingExerciseState

    private val _settingsIconState = MutableLiveData<SettingsIconState>()
    val settingsIconState: LiveData<SettingsIconState> = _settingsIconState

    init {
        val icons = resourcesRepository.icons()
        val colors = resourcesRepository.colors()
        _settingsIconState.value = SettingsIconState(icons = icons, colors = colors)
        _addingExerciseState.value = AddingExerciseState(
            icon = icons[0],
            color = colors[0],
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
        val model = _addingExerciseState.value?.model ?: return
        listRepository.saveExercise(model)
    }

}