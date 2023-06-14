package ru.freeit.crazytraining.exercise.list.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseEditButtonViewModel(
    private val editClickListener: (ExerciseModel) -> Unit,
    private val removeClickListener: (ExerciseModel) -> Unit
) {

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

        val buttons = if (imageResource == R.drawable.ic_close) {
            listOf(
                ExerciseEditButtonState.Button(R.string.edit, editClickListener),
                ExerciseEditButtonState.Button(R.string.remove, removeClickListener)
            )
        } else {
            listOf()
        }

        _state.value = ExerciseEditButtonState(
            imageResource = imageResource,
            buttons = buttons
        )
    }

}