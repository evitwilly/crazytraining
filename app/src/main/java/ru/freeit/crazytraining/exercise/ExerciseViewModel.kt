package ru.freeit.crazytraining.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.freeit.crazytraining.exercise.repository.ExerciseRepository
import ru.freeit.crazytraining.exercise.viewmodel_states.AddingExerciseState
import ru.freeit.crazytraining.exercise.viewmodel_states.SettingsIconState

class ExerciseViewModel(repository: ExerciseRepository) : ViewModel() {

    private val _addingExerciseState = MutableLiveData<AddingExerciseState>()
    val addingExerciseState: LiveData<AddingExerciseState> = _addingExerciseState

    private val _settingsIconState = MutableLiveData<SettingsIconState>()
    val settingsIconState: LiveData<SettingsIconState> = _settingsIconState

    init {
        val icons = repository.icons()
        val colors = repository.colors()
        _settingsIconState.value = SettingsIconState(icons = icons, colors = colors)
        _addingExerciseState.value = AddingExerciseState(icons[0], colors[0])
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

}