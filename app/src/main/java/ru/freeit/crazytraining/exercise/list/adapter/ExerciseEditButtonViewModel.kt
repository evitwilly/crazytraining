package ru.freeit.crazytraining.exercise.list.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.freeit.crazytraining.R

class ExerciseEditButtonViewModel(private val buttons: List<ExerciseEditButtonState.Button>) {

    private val _state = MutableLiveData<ExerciseEditButtonState>()
    val state: LiveData<ExerciseEditButtonState> = _state

    private var imageResource = R.drawable.ic_edit

    init {
        _state.value = ExerciseEditButtonState(
            imageResource = imageResource,
            buttons = listOf()
        )
    }

    fun toggle() {
        imageResource = if (imageResource == R.drawable.ic_edit)
            R.drawable.ic_close
        else
            R.drawable.ic_edit

        val buttons = if (imageResource == R.drawable.ic_close) buttons else emptyList()

        _state.value = ExerciseEditButtonState(
            imageResource = imageResource,
            buttons = buttons
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ExerciseEditButtonViewModel) return false

        return imageResource == other.imageResource
    }

    override fun hashCode() = imageResource

}