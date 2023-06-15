package ru.freeit.crazytraining.exercise.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseListState
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseListViewModel(private val repository: ExerciseListRepository) : BaseViewModel() {

    private val _exerciseListState = MutableLiveData<ExerciseListState>()
    val exerciseListState: LiveData<ExerciseListState> = _exerciseListState

    private var cachedModel: ExerciseModel? = null
    fun cache(model: ExerciseModel) {
        cachedModel = model
    }

    fun remove() = uiScope.launch {
        val model = cachedModel ?: return@launch
        repository.removeExercise(model)
        updateState()
    }

    fun updateState() = uiScope.launch {
        _exerciseListState.value = ExerciseListState(repository.exercises())
    }

}