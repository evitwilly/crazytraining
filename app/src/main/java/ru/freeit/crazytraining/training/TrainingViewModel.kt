package ru.freeit.crazytraining.training

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.core.viewmodel.SavedInstanceState
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepository
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.training.data.repository.ExerciseSetsRepository
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingTextState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingWeekendState

class TrainingViewModel(
    savedState: SavedInstanceState,
    private val exerciseListRepository: ExerciseListRepository,
    private val exerciseSetsRepository: ExerciseSetsRepository,
    private val calendarRepository: CalendarRepository,
    private val checkedWeekdaysRepository: CheckedWeekdaysRepository
) : BaseViewModel() {

    private val _textState = MutableLiveData<TrainingTextState>()
    val textState: LiveData<TrainingTextState> = _textState

    private val _trainingState = MutableLiveData<TrainingListState>()
    val trainingState: LiveData<TrainingListState> = _trainingState

    private val _weekendState = MutableLiveData<TrainingWeekendState>()
    val weekendState: LiveData<TrainingWeekendState> = _weekendState

    private var exerciseModel: ExerciseModel? = savedState.parcelable(exercise_key, ExerciseModel::class.java)
    fun cacheExercise(model: ExerciseModel) {
        exerciseModel = model
    }

    private var exerciseSetModel: ExerciseSetModel? = savedState.parcelable(exercise_set_key, ExerciseSetModel::class.java)
    fun cacheExerciseSet(model: ExerciseSetModel) {
        exerciseSetModel = model
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        bundle.putParcelable(exercise_key, exerciseModel)
        bundle.putParcelable(exercise_set_key, exerciseSetModel)
    }

    fun addSet(amount: Int) {
        val model = exerciseModel ?: return
        uiScope.launch {
            val millis = calendarRepository.dateTimeMillis()
            exerciseSetsRepository.saveExerciseSet(ExerciseSetModel(
                amount = amount,
                millis = millis,
                exerciseId = model.id,
                unit = model.unit,
                dateString = calendarRepository.dateStringFrom(millis),
                timeString = calendarRepository.timeStringFrom(millis)
            ))
            updateState()
        }
    }

    fun removeSet() {
        val model = exerciseSetModel ?: return
        uiScope.launch {
            exerciseSetsRepository.removeExerciseSet(model)
            updateState()
        }
    }

    fun plusSimilarSet(model: ExerciseSetModel) = uiScope.launch {
        val millis = calendarRepository.dateTimeMillis()
        exerciseSetsRepository.saveExerciseSet(model.copyWithSimilar(
            millis = millis,
            dateString = calendarRepository.dateStringFrom(millis),
            timeString = calendarRepository.timeStringFrom(millis)
        ))
        updateState()
    }

    fun minusSimilarSet(model: ExerciseSetModel) = uiScope.launch {
        exerciseSetsRepository.removeExerciseSet(model)
        updateState()
    }

    fun updateState() {
        val isTodayTraining = checkedWeekdaysRepository.readCheckedWeekdays().map { it.calendarVariable }.contains(calendarRepository.weekday())
        _textState.value = TrainingTextState(
            if (isTodayTraining) R.string.training else R.string.weekend,
            calendarRepository.weekdayMonthYearDateString()
        )
        _weekendState.value = if (isTodayTraining) TrainingWeekendState.Training else TrainingWeekendState.Weekend
        uiScope.launch {
            if (isTodayTraining) {
                _trainingState.value = exerciseListRepository.exercisesWithSetsByDate(calendarRepository.dateStringFrom())
            }
        }
    }

    private companion object {
        const val exercise_key = "exercise_key"
        const val exercise_set_key = "exercise_set_key"
    }

}