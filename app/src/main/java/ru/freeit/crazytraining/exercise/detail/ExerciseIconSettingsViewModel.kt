package ru.freeit.crazytraining.exercise.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseIconSettingsListState
import ru.freeit.crazytraining.exercise.detail.repository.ExerciseResourcesRepository

class ExerciseIconSettingsViewModel(
    private val repo: ExerciseResourcesRepository,
    checkedIconArg: Int,
    checkedColorArg: Int
) : BaseViewModel() {

    private val _listState = MutableLiveData<ExerciseIconSettingsListState>()
    val listState: LiveData<ExerciseIconSettingsListState> = _listState

    private var checkedIcon = checkedIconArg
    private var checkedColor = checkedColorArg

    private var iconsChecked = true

    val checked_icon_fragment_result: Int
        get() = checkedIcon

    val checked_color_fragment_result: Int
        get() = checkedColor

    init {
        updateState()
    }

    fun toggle() {
        iconsChecked = !iconsChecked
        updateState()
    }

    fun checkIcon(icon: Int) {
        checkedIcon = icon
        updateState()
    }

    fun checkColor(color: Int) {
        checkedColor = color
        updateState()
    }

    private fun updateState() {
        if (checkedIcon == 0)
            checkedIcon = repo.icons().first()

        if (checkedColor == 0)
            checkedColor = repo.colors().first()

        if (iconsChecked) {
            _listState.value = ExerciseIconSettingsListState.Icons(repo.icons(), checkedIcon, checkedColor)
        } else {
            _listState.value = ExerciseIconSettingsListState.Colors(repo.colors(), checkedIcon, checkedColor)
        }
    }

}