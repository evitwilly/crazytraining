package ru.freeit.crazytraining.core.theming.corners

import android.content.Context
import ru.freeit.crazytraining.core.extensions.dp

class CornerRadiusStyle(
    private val small: Float,
    private val medium: Float,
    private val big: Float,
    private val max: Float = 100f
) {

    fun style(ctx: Context, type: CornerRadiusType): Float {
        val dimension = when (type) {
            CornerRadiusType.small -> small
            CornerRadiusType.medium -> medium
            CornerRadiusType.big -> big
            CornerRadiusType.maximum -> max
        }
        return ctx.dp(dimension)
    }

}

