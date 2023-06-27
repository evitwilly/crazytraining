package ru.freeit.crazytraining.core.theming.layout.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.widget.LinearLayout
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy

open class CoreLinearLayout @JvmOverloads constructor(
    ctx: Context,
    private val backgroundColor: ColorAttributes = ColorAttributes.primaryBackgroundColor,
    private val shape: ShapeAttribute = ShapeAttribute.medium,
    private val shapeTreatmentStrategy: ShapeTreatmentStrategy = ShapeTreatmentStrategy.None(),
    private val rippleColor: ColorAttributes? = null,
): LinearLayout(ctx) {

    protected open fun onThemeChanged(theme: CoreTheme) {
        val gradientDrawable = GradientDrawable()
        val radius = shapeTreatmentStrategy.floatArrayOf(context.dp(theme.shapeStyle[shape]))
        gradientDrawable.cornerRadii = radius
        gradientDrawable.setColor(theme.colors[backgroundColor])

        background = if (rippleColor != null) {
            val maskDrawable = if (backgroundColor == ColorAttributes.transparent) {
                GradientDrawable().apply {
                    cornerRadii = radius
                    setColor(CoreColors.white)
                }
            } else {
                null
            }
            RippleDrawable(
                ColorStateList.valueOf(theme.colors[rippleColor]),
                gradientDrawable,
                maskDrawable
            )
        } else {
            gradientDrawable
        }

    }

    protected val themeManager = (context.applicationContext as App).themeManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

}