package ru.freeit.crazytraining.training

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.mocks.*
import ru.freeit.crazytraining.core.models.WeekdayModel
import ru.freeit.crazytraining.core.rules.MainDispatcherRule
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.training.model.TrainingModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingListState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingTextState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingActiveState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailState

internal class TrainingViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule: TestRule = MainDispatcherRule()

    @Test
    fun `test updateState when today training is active`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(
            calendarVariable = 2,
            date = date,
            dateString = "11.11.2111"
        )
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val trainings = listOf(TrainingModel(id = 1, millis = 10, date = "11.11.2111"))
        val detailStates = listOf(TrainingDetailState(ExerciseModel(), listOf(ExerciseSetModel(amount = 1), ExerciseSetModel(amount = 2))))

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository,
            TrainingRepositoryMock(trainings = trainings, listStates = detailStates)
        )
        viewModel.updateState()

        assertEquals(TrainingTextState(R.string.training, date), viewModel.textState.value)
        assertEquals(TrainingActiveState.Training, viewModel.activeState.value)
        assertEquals(TrainingListState(detailStates), viewModel.listState.value)
    }

    @Test
    fun `test updateState when today training is active and yesterday training is also active`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(
            calendarVariable = 2,
            date = date,
            dateString = "11.11.2111",
            dateStringWithoutDays = "10.11.2111"
        )
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val trainings = listOf(
            TrainingModel(id = 1, millis = 10, date = "11.11.2111"),
            TrainingModel(id = 1, millis = 10, date = "10.11.2111")
        )
        val detailStates = listOf(TrainingDetailState(ExerciseModel(), listOf(ExerciseSetModel(amount = 1), ExerciseSetModel(amount = 2))))

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository,
            TrainingRepositoryMock(trainings = trainings, listStates = detailStates)
        )
        viewModel.updateState()

        assertEquals(TrainingTextState(R.string.training, date), viewModel.textState.value)
        assertEquals(TrainingActiveState.Training, viewModel.activeState.value)
        assertEquals(TrainingListState(detailStates), viewModel.listState.value)
        assertEquals("finishing_yesterday_training", viewModel.isVisibleFinishingTrainingDialog.value)
    }

    @Test
    fun `test updateState when today has not training`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(
            calendarVariable = 2,
            date = date,
            millis = 1000,
            dateString = "11.11.2111"
        )
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val detailStates = listOf(TrainingDetailState(ExerciseModel(), listOf(ExerciseSetModel(amount = 1), ExerciseSetModel(amount = 2))))

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository,
            TrainingRepositoryMock(listStates = detailStates)
        )
        viewModel.updateState()

        assertEquals(TrainingTextState(R.string.training, date), viewModel.textState.value)
        assertEquals(TrainingActiveState.Training, viewModel.activeState.value)
        assertEquals(TrainingListState(detailStates), viewModel.listState.value)
    }

    @Test
    fun `test updateState when today training has finished`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(
            calendarVariable = 2,
            date = date,
            dateString = "11.11.2111"
        )
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val trainings = listOf(TrainingModel(id = 1, millis = 10, date = "11.11.2111", active = false))
        val detailStates = listOf(TrainingDetailState(ExerciseModel(), listOf(ExerciseSetModel(amount = 1), ExerciseSetModel(amount = 2))))

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository,
            TrainingRepositoryMock(trainings = trainings, listStates = detailStates)
        )
        viewModel.updateState()

        assertEquals(TrainingTextState(R.string.training, date), viewModel.textState.value)
        assertEquals(TrainingActiveState.Finished, viewModel.activeState.value)
        assertEquals(TrainingListState(detailStates), viewModel.listState.value)
    }

    @Test
    fun `test title when today is weekend`() {
        val calendar = CalendarRepositoryMock(1)
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository,
            TrainingRepositoryMock()
        )
        viewModel.updateState()

        assertEquals(TrainingTextState(R.string.weekend, ""), viewModel.textState.value)
        assertEquals(TrainingActiveState.Weekend, viewModel.activeState.value)
    }

    @Test
    fun `test addSet`() {
        val exerciseSetsRepository = ExerciseSetsRepositoryMock()

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            exerciseSetsRepository,
            CalendarRepositoryMock(
                calendarVariable = 1,
                millis = 100,
                timeString = "22:22",
                dateString = "11.11.1111"
            ),
            CheckedWeekdaysRepositoryMock(WeekdayModel.values().toList()),
            TrainingRepositoryMock(listOf(TrainingModel(
                millis = 1000,
                date = "11.11.1111",
                id = 1
            )))
        )

        viewModel.updateState()

        viewModel.cacheExercise(ExerciseModel(id = 10, unit = ExerciseUnitModel.TIME))

        viewModel.addSet(180)

        val expected = listOf(ExerciseSetModel(
            amount = 180,
            exerciseId = 10,
            trainingId = 1,
            unit = ExerciseUnitModel.TIME,
            millis = 100,
            dateString = "11.11.1111",
            timeString = "22:22"
        ))
        assertEquals(expected, exerciseSetsRepository.data)
        assertEquals(TrainingListState(emptyList()), viewModel.listState.value)
    }

    @Test
    fun `test removeSet`() {
        val exerciseSetsRepository = ExerciseSetsRepositoryMock()
        val exerciseSet = ExerciseSetModel(
            id = 1000,
            amount = 1000,
            millis = 200,
            exerciseId = 5,
            unit = ExerciseUnitModel.DISTANCE
        )
        exerciseSetsRepository.data.add(exerciseSet)

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            exerciseSetsRepository,
            CalendarRepositoryMock(calendarVariable = 1),
            CheckedWeekdaysRepositoryMock(WeekdayModel.values().toList()),
            TrainingRepositoryMock()
        )

        viewModel.cacheExerciseSet(exerciseSet)

        viewModel.removeSet()

        assertEquals(emptyList<ExerciseSetModel>(), exerciseSetsRepository.data)
        assertEquals(TrainingListState(emptyList()), viewModel.listState.value)
    }

    @Test
    fun `test plusSimilarSet`() {
        val exerciseSetsRepository = ExerciseSetsRepositoryMock()
        val exerciseSet = ExerciseSetModel(
            id = 1000,
            amount = 1000,
            millis = 200,
            exerciseId = 5,
            unit = ExerciseUnitModel.DISTANCE,
            dateString = "22:22:2222",
            timeString = "22:22"
        )
        exerciseSetsRepository.data.add(exerciseSet)

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            exerciseSetsRepository,
            CalendarRepositoryMock(
                calendarVariable = 1,
                millis = 500,
                dateString = "11:11:1111",
                timeString = "11:11"
            ),
            CheckedWeekdaysRepositoryMock(WeekdayModel.values().toList()),
            TrainingRepositoryMock()
        )

        viewModel.plusSimilarSet(exerciseSet)

        val expected = listOf(
            exerciseSet,
            ExerciseSetModel(
                id = 0,
                amount = 1000,
                millis = 500,
                exerciseId = 5,
                unit = ExerciseUnitModel.DISTANCE,
                dateString = "11:11:1111",
                timeString = "11:11"
            )
        )
        assertEquals(expected, exerciseSetsRepository.data)
        assertEquals(TrainingListState(emptyList()), viewModel.listState.value)
    }

    @Test
    fun `test buttonClick when today training has not finished`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(
            calendarVariable = 2,
            date = date,
            dateString = "11.11.2111"
        )
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val trainings = listOf(TrainingModel(id = 1, millis = 10, date = "11.11.2111", active = true))
        val detailStates = listOf(TrainingDetailState(ExerciseModel(), listOf(ExerciseSetModel(amount = 1), ExerciseSetModel(amount = 2))))

        val trainingRepository = TrainingRepositoryMock(trainings = trainings, listStates = detailStates)

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository,
            trainingRepository
        )
        viewModel.updateState()

        viewModel.buttonClick()

        assertEquals("finishing_today_training", viewModel.isVisibleFinishingTrainingDialog.value)
    }

    @Test
    fun `test buttonClick when today training has finished`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(
            calendarVariable = 2,
            date = date,
            dateString = "11.11.2111"
        )
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val trainings = listOf(TrainingModel(id = 1, millis = 10, date = "11.11.2111", active = false))
        val detailStates = listOf(TrainingDetailState(ExerciseModel(), listOf(ExerciseSetModel(amount = 1), ExerciseSetModel(amount = 2))))

        val trainingRepository = TrainingRepositoryMock(trainings = trainings, listStates = detailStates)

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository,
            trainingRepository
        )
        viewModel.updateState()

        trainingRepository.items.clear()

        viewModel.buttonClick()

        val expected = listOf(TrainingModel(id = 1, millis = 10, date = "11.11.2111", active = true))
        assertEquals(expected, trainingRepository.items)
    }

    @Test
    fun `test finishTraining when yesterday training has not finished`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(
            calendarVariable = 2,
            date = date,
            dateString = "11.11.2111",
            millis = 100,
            dateStringWithoutDays = "10.11.2111"
        )
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val trainings = listOf(
            TrainingModel(id = 1, millis = 10, date = "11.11.2111"),
            TrainingModel(id = 2, millis = 10, date = "10.11.2111")
        )
        val detailStates = listOf(TrainingDetailState(ExerciseModel(), listOf(ExerciseSetModel(amount = 1), ExerciseSetModel(amount = 2))))

        val trainingRepository = TrainingRepositoryMock(trainings = trainings, listStates = detailStates)
        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository,
            trainingRepository
        )
        viewModel.updateState()

        trainingRepository.items.clear()

        viewModel.finishTraining("finishing_yesterday_training", "comment", 5)

        val expected = listOf(
            TrainingModel(id = 1, millis = 10, date = "10.11.2111", comment = "comment", rating = 5f, active = false),
            TrainingModel(id = 2, millis = 100, date = "11.11.2111", active = true)
        )
        assertEquals(expected, trainingRepository.items)
    }

    @Test
    fun `test finishTraining when today training has not finished`() {
        val date = "Saturday, 3 June, 2023"
        val calendar = CalendarRepositoryMock(
            calendarVariable = 2,
            date = date,
            dateString = "11.11.2111"
        )
        val checkedWeekdaysRepository = CheckedWeekdaysRepositoryMock(mutableListOf(
            WeekdayModel.MONDAY,
            WeekdayModel.WEDNESDAY,
            WeekdayModel.FRIDAY
        ))

        val trainings = listOf(TrainingModel(id = 1, millis = 10, date = "11.11.2111"))
        val detailStates = listOf(TrainingDetailState(ExerciseModel(), listOf(ExerciseSetModel(amount = 1), ExerciseSetModel(amount = 2))))

        val trainingRepository = TrainingRepositoryMock(trainings = trainings, listStates = detailStates)

        val viewModel = TrainingViewModel(
            SavedInstanceStateMock(),
            ExerciseSetsRepositoryMock(),
            calendar,
            checkedWeekdaysRepository,
            trainingRepository
        )
        viewModel.updateState()

        trainingRepository.items.clear()

        viewModel.finishTraining("finishing_today_training", "comment", 3)

        val expected = listOf(
            TrainingModel(id = 1, millis = 10, date = "11.11.2111", comment = "comment", rating = 3f, active = false)
        )
        assertEquals(expected, trainingRepository.items)
    }

}