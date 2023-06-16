package ru.freeit.crazytraining.exercise.list.adapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.freeit.crazytraining.R

internal class ExerciseEditButtonViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `test toggle`() {
        val buttons = listOf(ExerciseEditButtonState.Button(1) {})
        val viewModel = ExerciseEditButtonViewModel(buttons)

        assertEquals(ExerciseEditButtonState(R.drawable.ic_edit, emptyList()), viewModel.state.value)

        viewModel.toggle()

        assertEquals(ExerciseEditButtonState(R.drawable.ic_close, buttons), viewModel.state.value)
    }

}