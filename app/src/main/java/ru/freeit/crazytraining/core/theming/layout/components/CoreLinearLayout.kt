package ru.freeit.crazytraining.core.theming.layout.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.widget.LinearLayout
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy

open class CoreLinearLayout @JvmOverloads constructor(
    ctx: Context,
    private val backgroundColor: ColorType = primaryBackgroundColor,
    private val cornerRadiusStyle: CornerRadiusType = CornerRadiusType.medium,
    private val cornerTreatmentStrategy: CornerTreatmentStrategy = CornerTreatmentStrategy.None(),
    private val rippleColor: ColorType? = null,
): LinearLayout(ctx) {

    protected open fun onThemeChanged(theme: CoreTheme) {
        val gradientDrawable = GradientDrawable()
        val radius = cornerTreatmentStrategy.floatArrayOf(theme.cornerRadiusStyle.style(context, cornerRadiusStyle))
        gradientDrawable.cornerRadii = radius
        gradientDrawable.setColor(theme.colorsStyle.color(backgroundColor))

        background = if (rippleColor != null) {
            val maskDrawable = if (backgroundColor == transparent) {
                GradientDrawable().apply {
                    cornerRadii = radius
                    setColor(CoreColors.white)
                }
            } else {
                null
            }
            RippleDrawable(
                ColorStateList.valueOf(theme.colorsStyle.color(rippleColor)),
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