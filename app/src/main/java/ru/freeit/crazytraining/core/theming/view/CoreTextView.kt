package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.extensions.fontSize
import ru.freeit.crazytraining.core.theming.text.TextAttribute

open class CoreTextView @JvmOverloads constructor(
    ctx: Context,
    private var textColor: ColorAttributes = ColorAttributes.primaryTextColor,
    private val textStyle: TextAttribute = TextAttribute.Body1
): AppCompatTextView(ctx) {

    protected val themeManager = (context.applicationContext as App).themeManager
    private val typefaceManager = (context.applicationContext as App).typefaceManager

    init {
        includeFontPadding = false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    protected open fun onThemeChanged(theme: CoreTheme) {
        val (fontFamily, textSize) = theme.textStyle[textStyle]
        typeface = typefaceManager.typeface(fontFamily)
        fontSize(textSize)
        setTextColor(theme.colors[textColor])
    }

    fun changeTextColor(color: ColorAttributes) {
        textColor = color
        setTextColor(themeManager.selected_theme.colors[color])
    }

}