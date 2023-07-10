package ru.freeit.crazytraining.exercise.list

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.core.viewmodel.SavedInstanceState
import ru.freeit.crazytraining.exercise.data.repository.ExerciseActiveRepository
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseListState
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListViewModel(
    savedState: SavedInstanceState,
    private val exerciseRepository: ExerciseListRepository,
    private val activeRepository: ExerciseActiveRepository
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

    fun changeStatus(model: ExerciseModel, active: Boolean) = uiScope.launch {
        activeRepository.checkActive(model.id, active)
        updateState()
    }

    fun remove() = uiScope.launch {
        val model = cachedModel ?: return@launch
        exerciseRepository.removeExercise(model)
        updateState()
    }

    fun updateState() = uiScope.launch {
        _exerciseListState.value = exerciseRepository.exercises()
    }

    private companion object {
        const val cached_model_key = "cached_model_key"
    }

}