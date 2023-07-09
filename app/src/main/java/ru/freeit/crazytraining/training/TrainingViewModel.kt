package ru.freeit.crazytraining.training

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.fragment.BaseViewModel
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.core.viewmodel.SavedInstanceState
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.training.data.repository.ExerciseSetsRepository
import ru.freeit.crazytraining.training.data.repository.TrainingRepository
import ru.freeit.crazytraining.training.model.TrainingModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingTextState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingActiveState

class TrainingViewModel(
    savedState: SavedInstanceState,
    private val exerciseSetsRepository: ExerciseSetsRepository,
    private val calendarRepository: CalendarRepository,
    private val checkedWeekdaysRepository: CheckedWeekdaysRepository,
    private val trainingRepository: TrainingRepository
) : BaseViewModel() {

    private val _textState = MutableLiveData<TrainingTextState>()
    val textState: LiveData<TrainingTextState> = _textState

    private val _listState = MutableLiveData<TrainingListState>()
    val listState: LiveData<TrainingListState> = _listState

    private val _activeState = MutableLiveData<TrainingActiveState>()
    val activeState: LiveData<TrainingActiveState> = _activeState

    private val _isVisibleFinishingTrainingDialog = MutableLiveData<String>()
    val isVisibleFinishingTrainingDialog: LiveData<String> = _isVisibleFinishingTrainingDialog

    private var todayTrainingModel: TrainingModel = TrainingModel()
    private var yesterdayTrainingModel: TrainingModel = TrainingModel()

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
        val trainingId = todayTrainingModel.id
        if (trainingId <= 0) return
        uiScope.launch {
            val millis = calendarRepository.nowDateTimeMillis()
            exerciseSetsRepository.saveExerciseSet(ExerciseSetModel(
                amount = amount,
                millis = millis,
                exerciseId = model.id,
                trainingId = trainingId,
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
        val millis = calendarRepository.nowDateTimeMillis()
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

    fun finishTraining(trainingType: String, comment: String = "", rating: Int = 4) = uiScope.launch {
        val training = if (trainingType == finishing_today_training) todayTrainingModel else yesterdayTrainingModel
        trainingRepository.saveTraining(training.copy(
            comment = comment,
            rating = rating.toFloat(),
            active = false
        ))
        updateState()
    }

    fun buttonClick() = uiScope.launch {
        if (todayTrainingModel.hasNotFinished) {
            _isVisibleFinishingTrainingDialog.value = finishing_today_training
        } else {
            trainingRepository.saveTraining(todayTrainingModel.copy(active = true))
            updateState()
        }
    }

    fun updateState() {
        val isTodayTraining = checkedWeekdaysRepository.readCheckedWeekdays().map { it.calendarVariable }.contains(calendarRepository.weekday())

        _textState.value = TrainingTextState(
            if (isTodayTraining) R.string.training else R.string.weekend,
            calendarRepository.weekdayMonthYearDateString()
        )

        uiScope.launch {
            val todayMillis = calendarRepository.nowDateTimeMillis()

            val yesterdayTraining = trainingRepository.trainingByDate(calendarRepository.dateStringWithoutDays(todayMillis, 1))
            if (yesterdayTraining.hasNotFinished) {
                yesterdayTrainingModel = yesterdayTraining
                _isVisibleFinishingTrainingDialog.value = finishing_yesterday_training
            }

            if (isTodayTraining) {
                val todayDate = calendarRepository.dateStringFrom(todayMillis)
                val todayTraining = trainingRepository.trainingByDate(todayDate)
                todayTrainingModel = if (todayTraining.isEmpty) {
                    val newTodayTraining = TrainingModel(millis = todayMillis, date = todayDate)

                    trainingRepository.saveTraining(newTodayTraining)

                    trainingRepository.trainingByDate(todayDate)
                } else {
                    todayTraining
                }

                _activeState.value = if (todayTrainingModel.hasNotFinished) {
                    TrainingActiveState.Training
                } else {
                    TrainingActiveState.Finished
                }

                _listState.value = trainingRepository.exercisesWithSetsByTraining(todayTrainingModel.id)
            } else {
                _activeState.value = TrainingActiveState.Weekend
            }
        }
    }

    private companion object {
        const val exercise_key = "exercise_key"
        const val exercise_set_key = "exercise_set_key"

        const val finishing_yesterday_training = "finishing_yesterday_training"
        const val finishing_today_training = "finishing_today_training"
    }

}