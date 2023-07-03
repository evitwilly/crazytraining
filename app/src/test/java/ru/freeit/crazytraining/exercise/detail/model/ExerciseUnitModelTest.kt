package ru.freeit.crazytraining.exercise.detail.model

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.ResourcesProvider

internal class ExerciseUnitModelTest {

    class ResourcesProviderMock : ResourcesProvider {

        override fun string(stringResource: Int, vararg args: Any): String = ""

        override fun quantityString(stringResource: Int, quantity: Int, vararg args: Any): String {
            return when(stringResource) {
                R.plurals.meters, R.plurals.seconds, R.plurals.minutes -> quantity.toString()
                R.plurals.kilometers -> args.first().toString()
                else -> ""
            }
        }

    }

    @Test
    fun `test distance amountConverterString`() {
        val amountConverterString = ExerciseUnitModel.DISTANCE.amountConverterString
        val resources = ResourcesProviderMock()

        val actual1 = amountConverterString.invoke(resources, 100)
        assertEquals("100", actual1)

        val actual2 = amountConverterString.invoke(resources, 1000)
        assertEquals("1", actual2)

        val actual3 = amountConverterString.invoke(resources, 1500)
        assertEquals("1.5", actual3)

        val actual4 = amountConverterString.invoke(resources, 6809)
        assertEquals("6.809", actual4)

        val actual5 = amountConverterString.invoke(resources, 20_000)
        assertEquals("20", actual5)
    }

    @Test
    fun `test time amountConverterString`() {
        val amountConverterString = ExerciseUnitModel.TIME.amountConverterString
        val resources = ResourcesProviderMock()

        val actual1 = amountConverterString.invoke(resources, 45)
        assertEquals("45", actual1)

        val actual2 = amountConverterString.invoke(resources, 60)
        assertEquals("1", actual2)

        val actual3 = amountConverterString.invoke(resources, 90)
        assertEquals("1 30", actual3)

        val actual4 = amountConverterString.invoke(resources, 180)
        assertEquals("3", actual4)

        val actual5 = amountConverterString.invoke(resources, 436)
        assertEquals("7 16", actual5)
    }

}