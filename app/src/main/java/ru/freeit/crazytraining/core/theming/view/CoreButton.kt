package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.text.TextType.Caption

class CoreButton @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val cornerRadiusType: CornerRadiusType = CornerRadiusType.medium,
    private val cornerTreatmentStrategy: CornerTreatmentStrategy = CornerTreatmentStrategy.None()
) : CoreTextView(ctx, attrs, defStyleAttr, textColor = colorOnPrimary, textStyle = Caption) {

    init {
        isClickable = true
        isFocusable = true
        includeFontPadding = false
        padding(horizontal = context.dp(8), vertical = context.dp(12))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)

        val gradientBackground = GradientDrawable()
        gradientBackground.setColor(theme.colorsStyle.color(primaryColor))
        gradientBackground.cornerRadii = cornerTreatmentStrategy.floatArrayOf(theme.cornerRadiusStyle.value(cornerRadiusType))

        val rippleColor = ColorStateList.valueOf(theme.colorsStyle.color(primaryDarkColor))

        background = RippleDrawable(rippleColor, gradientBackground, null)
    }

    fun changeStartIcon(@DrawableRes drawableRes: Int, size: Int = 16) {
        val drawable = AppCompatResources.getDrawable(context, drawableRes) ?: return
        drawable.setBounds(0, 0, context.dp(size), context.dp(size))
        drawable.setTint(themeManager.selected_theme.colorsStyle.color(colorOnPrimary))
        setCompoundDrawables(drawable, null, null, null)
        compoundDrawablePadding = context.dp(4)
    }

}