package ru.freeit.crazytraining.core.theming.layout.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.FrameLayout
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy

class CoreFrameLayout @JvmOverloads constructor(
    ctx: Context,
    private val backgroundColor: ColorAttributes = ColorAttributes.primaryBackgroundColor,
    private val shape: ShapeAttribute = ShapeAttribute.medium,
    private val shapeTreatmentStrategy: ShapeTreatmentStrategy = ShapeTreatmentStrategy.None()
): FrameLayout(ctx) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadii = shapeTreatmentStrategy.floatArrayOf(ctx.dp(theme.shapeStyle[shape]))
        gradientDrawable.setColor(theme.colors[backgroundColor])
        background = gradientDrawable
    }

    private val themeManager = (context.applicationContext as App).themeManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(onThemeChanged)
    }

}