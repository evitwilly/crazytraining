package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes.primaryColor
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy

class CoreImageButtonView @JvmOverloads constructor(
    ctx: Context,
    private val shape: ShapeAttribute = ShapeAttribute.maximum,
    private val shapeTreatmentStrategy: ShapeTreatmentStrategy = ShapeTreatmentStrategy.AllRounded(),
    private val rippleColor: ColorAttributes = primaryColor,
    private val backgroundColor: ColorAttributes? = null,
    tintColor: ColorAttributes = ColorAttributes.primaryTextColor
): CoreImageView(ctx, tintColor = tintColor) {

    init {
        isClickable = true
        isFocusable = true
    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)

        val rippleColor = ColorStateList.valueOf(theme.colors[rippleColor])

        val radius = context.dp(theme.shapeStyle[shape])
        val maskBackground = GradientDrawable().apply {
            setColor(CoreColors.white)
            cornerRadii = shapeTreatmentStrategy.floatArrayOf(radius)
        }

        val contentGradient = if (backgroundColor != null) {
            val drawable = GradientDrawable()
            drawable.cornerRadii = shapeTreatmentStrategy.floatArrayOf(radius)
            drawable.setColor(theme.colors[backgroundColor])
            drawable
        } else {
            null
        }

        background = RippleDrawable(rippleColor, contentGradient, maskBackground)
    }

}