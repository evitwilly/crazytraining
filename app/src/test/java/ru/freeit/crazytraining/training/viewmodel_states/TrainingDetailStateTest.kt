package ru.freeit.crazytraining.training.viewmodel_states

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.freeit.crazytraining.exercise.detail.model.ExerciseUnitModel
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel

internal class TrainingDetailStateTest {

    @Test
    fun `test sorted_sets_by_number`() {
        val state = TrainingDetailState(
            model = ExerciseModel(),
            sets = listOf(
                ExerciseSetModel(millis = 1, amount = 100),
                ExerciseSetModel(millis = 2, amount = 100),
                ExerciseSetModel(millis = 3, amount = 50),
                ExerciseSetModel(millis = 4, amount = 100),
                ExerciseSetModel(millis = 5, amount = 1000),
                ExerciseSetModel(millis = 6, amount = 50),
                ExerciseSetModel(millis = 7, amount = 38),
                ExerciseSetModel(millis = 10, amount = 1000),
                ExerciseSetModel(millis = 11, amount = 50),
                ExerciseSetModel(millis = 12, amount = 50),
                ExerciseSetModel(millis = 13, amount = 5)
            )
        )

        val expected = listOf(
            ExerciseSetModel(millis = 4, amount = 100) to 3,
            ExerciseSetModel(millis = 12, amount = 50) to 4,
            ExerciseSetModel(millis = 10, amount = 1000) to 2,
            ExerciseSetModel(millis = 7, amount = 38) to 1,
            ExerciseSetModel(millis = 13, amount = 5) to 1
        )
        assertEquals(expected, state.sorted_sets_by_number)
    }

    @Test
    fun `test model_with_total_amount`() {
        val state = TrainingDetailState(
            model = ExerciseModel(unit = ExerciseUnitModel.TIME),
            sets = listOf(
                ExerciseSetModel(millis = 1, amount = 100),
                ExerciseSetModel(millis = 2, amount = 100),
                ExerciseSetModel(millis = 3, amount = 200),
                ExerciseSetModel(millis = 4, amount = 200),
                ExerciseSetModel(millis = 5, amount = 1000),
                ExerciseSetModel(millis = 6, amount = 1500),
                ExerciseSetModel(millis = 7, amount = 5000)
            )
        )

        val expected = ExerciseSetModel(
            amount = 8100,
            unit = ExerciseUnitModel.TIME
        )

        assertEquals(expected, state.model_with_total_amount)
    }

}