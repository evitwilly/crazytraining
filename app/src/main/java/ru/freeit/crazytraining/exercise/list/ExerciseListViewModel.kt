package ru.freeit.crazytraining.exercise.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.core.navigation.BaseViewModel
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseListState

class ExerciseListViewModel(private val repository: ExerciseListRepository) : BaseViewModel() {

    private val _exerciseListState = MutableLiveData<ExerciseListState>()
    val exerciseListState: LiveData<ExerciseListState> = _exerciseListState

    fun updateState() {
        uiScope.launch {
            _exerciseListState.value = ExerciseListState(repository.exercises())
        }
    }

}