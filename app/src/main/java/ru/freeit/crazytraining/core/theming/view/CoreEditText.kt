package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import androidx.appcompat.widget.AppCompatEditText
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.fontSize
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.text.TextType

class CoreEditText @JvmOverloads constructor(
    ctx: Context,
    private val textStyle: TextType = TextType.Body1
) : AppCompatEditText(ctx) {

    private val themeManager = (context.applicationContext as App).themeManager
    private val typefaceManager = (context.applicationContext as App).typefaceManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    private fun onThemeChanged(theme: CoreTheme) {
        val textColor = theme.colorsStyle.color(primaryTextColor)
        setHintTextColor(textColor)
        setTextColor(textColor)

        val (fontFamily, textSize) = theme.textStyle.style(textStyle)
        typeface = typefaceManager.typeface(fontFamily)
        fontSize(textSize)

        background = InsetDrawable(
            GradientDrawable().apply {
                setStroke(context.dp(2), theme.colorsStyle.color(primaryColor))
            },
            -context.dp(2),
            -context.dp(2),
            -context.dp(2),
            0
        )
        includeFontPadding = false
        padding(horizontal = context.dp(2), vertical = context.dp(8))
    }

}