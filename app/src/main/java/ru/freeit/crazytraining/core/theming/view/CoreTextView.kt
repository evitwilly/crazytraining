package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle

open class CoreTextView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val textColor: ColorType = primaryTextColor
): AppCompatTextView(ctx, attrs, defStyleAttr) {

    protected val themeManager = (context.applicationContext as App).themeManager
    protected val typefaceManager = (context.applicationContext as App).typefaceManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    protected open fun onThemeChanged(theme: CoreTheme) {
        setTextColor(theme.colorsStyle.color(textColor))
    }

    fun fontFamily(style: TypefaceStyle) {
        typeface = typefaceManager.typeface(style)
    }

}