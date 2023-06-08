package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.extensions.fontSize
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.text.TextType.*

open class CoreTextView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val textColor: ColorType = primaryTextColor,
    private val textStyle: TextType = Body1
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
        val (fontFamily, textSize) = theme.textStyle.style(textStyle)
        typeface = typefaceManager.typeface(fontFamily)
        fontSize(textSize)
        setTextColor(theme.colorsStyle.color(textColor))
    }

}