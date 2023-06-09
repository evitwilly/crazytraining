package ru.freeit.crazytraining.core.theming.corners

class CornerRadiusStyle(
    private val small: Float,
    private val medium: Float,
    private val big: Float,
    private val max: Float = 100f
) {

    fun style(type: CornerRadiusType): Float {
        return when (type) {
            CornerRadiusType.small -> small
            CornerRadiusType.medium -> medium
            CornerRadiusType.big -> big
            CornerRadiusType.maximum -> max
        }
    }

}

