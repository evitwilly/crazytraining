package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType.primaryColor
import ru.freeit.crazytraining.core.theming.colors.ColorType.primaryTextColor
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.theming.extensions.roundRipple

class CoreImageButtonView @JvmOverloads constructor(
    ctx: Context,
    private val cornerRadiusType: CornerRadiusType = CornerRadiusType.maximum,
    private val cornerTreatmentStrategy: CornerTreatmentStrategy = CornerTreatmentStrategy.AllRounded()
): CoreImageView(ctx) {

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)

        val rippleColor = ColorStateList.valueOf(theme.colorsStyle.color(primaryTextColor))

        val maskBackground = GradientDrawable().apply {
            cornerRadii = cornerTreatmentStrategy.floatArrayOf(theme.cornerRadiusStyle.style(cornerRadiusType))
            setColor(CoreColors.white)
        }

        background = RippleDrawable(rippleColor, null, maskBackground)

        roundRipple(theme.colorsStyle.color(primaryColor))
    }

}