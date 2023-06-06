package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.fontSize
import ru.freeit.crazytraining.core.theming.extensions.padding

class CoreButton(ctx: Context) : AppCompatTextView(ctx) {

    private val themeManager = (context.applicationContext as App).themeManager
    private val typefaceManager = (context.applicationContext as App).typefaceManager

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

    private fun onThemeChanged(theme: CoreTheme) {
        setTextColor(theme.primaryButtonTextColor)
        background = RippleDrawable(
            ColorStateList.valueOf(theme.primaryButtonRippleColor),
            GradientDrawable().apply { setColor(theme.primaryButtonBackgroundColor) },
            null
        )
        typeface = typefaceManager.typeface(theme.primaryButtonTextStyle)
        fontSize(theme.primaryButtonTextSize)
    }

    fun changeStartIcon(@DrawableRes drawableRes: Int, size: Int = 16) {
        val drawable = AppCompatResources.getDrawable(context, drawableRes) ?: return
        drawable.setBounds(0, 0, context.dp(size), context.dp(size))
        drawable.setTint(themeManager.selected_theme.primaryButtonTextColor)
        setCompoundDrawables(drawable, null, null, null)
        compoundDrawablePadding = context.dp(4)
    }

}