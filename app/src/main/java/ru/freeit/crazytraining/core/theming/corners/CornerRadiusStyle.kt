package ru.freeit.crazytraining.core.theming.corners

class CornerRadiusStyle(
    private val small: Float,
    private val medium: Float,
    private val big: Float
) {

    fun value(type: CornerRadiusType): Float {
        return when (type) {
            CornerRadiusType.small -> small
            CornerRadiusType.medium -> medium
            CornerRadiusType.big -> big
        }
    }

}

