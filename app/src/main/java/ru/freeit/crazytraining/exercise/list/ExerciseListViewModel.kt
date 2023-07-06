package ru.freeit.crazytraining.exercise.list

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.core.viewmodel.SavedInstanceState
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.list.adapter.ExerciseEditButtonState
import ru.freeit.crazytraining.exercise.list.adapter.ExerciseEditButtonViewModel
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseDetailState
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseListState
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListViewModel(
    savedState: SavedInstanceState,
    private val repository: ExerciseListRepository,
    private val itemButtons: List<ExerciseEditButtonState.Button>
) : BaseViewModel() {

    private val _exerciseListState = MutableLiveData<ExerciseListState>()
    val exerciseListState: LiveData<ExerciseListState> = _exerciseListState

    override fun onSaveInstanceState(bundle: Bundle) {
        bundle.putParcelable(cached_model_key, cachedModel)
    }

    private var cachedModel: ExerciseModel? = savedState.parcelable(cached_model_key, ExerciseModel::class.java)
    fun cache(model: ExerciseModel) {
        cachedModel = model
    }

    fun remove() = uiScope.launch {
        val model = cachedModel ?: return@launch
        repository.removeExercise(model)
        updateState()
    }

    fun updateState() = uiScope.launch {
        _exerciseListState.value = ExerciseListState(
            repository.exercises().map {
                ExerciseDetailState(it, ExerciseEditButtonViewModel(itemButtons).apply { toggle() })
            }
        )
    }

    private companion object {
        const val cached_model_key = "cached_model_key"
    }

}