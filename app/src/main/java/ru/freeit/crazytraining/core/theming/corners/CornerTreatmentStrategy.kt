package ru.freeit.crazytraining.core.theming.corners

interface CornerTreatmentStrategy {
    fun floatArrayOf(radius: Float): FloatArray

    class None : CornerTreatmentStrategy {
        override fun floatArrayOf(radius: Float) = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    }

    class AllRounded : CornerTreatmentStrategy {
        override fun floatArrayOf(radius: Float) = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
    }

    class AllElliptical : CornerTreatmentStrategy {
        override fun floatArrayOf(radius: Float) = floatArrayOf(radius / 2f, radius, radius / 2f, radius, radius / 2f, radius, radius / 2f, radius)
    }

    class EndElliptical : CornerTreatmentStrategy {
        override fun floatArrayOf(radius: Float) = floatArrayOf(0f, 0f, radius / 2f, radius, radius / 2f, radius, 0f, 0f)
    }

    class StartElliptical : CornerTreatmentStrategy {
        override fun floatArrayOf(radius: Float) = floatArrayOf(radius / 2f, radius, 0f, 0f, 0f, 0f, radius / 2f, radius)
    }

}