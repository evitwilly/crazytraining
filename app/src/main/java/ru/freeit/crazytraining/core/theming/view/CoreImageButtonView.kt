package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes.primaryColor
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy

class CoreImageButtonView @JvmOverloads constructor(
    ctx: Context,
    private val shape: ShapeAttribute = ShapeAttribute.maximum,
    private val shapeTreatmentStrategy: ShapeTreatmentStrategy = ShapeTreatmentStrategy.AllRounded()
): CoreImageView(ctx) {

    init {
        isClickable = true
        isFocusable = true
    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)

        val rippleColor = ColorStateList.valueOf(theme.colors[primaryColor])

        val radius = context.dp(theme.shapeStyle[shape])
        val maskBackground = GradientDrawable().apply {
            setColor(CoreColors.white)
            cornerRadii = shapeTreatmentStrategy.floatArrayOf(radius)
        }

        background = RippleDrawable(rippleColor, null, maskBackground)
    }

}