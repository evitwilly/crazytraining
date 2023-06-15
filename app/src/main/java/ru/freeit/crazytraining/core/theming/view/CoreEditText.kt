package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextType

class CoreEditText @JvmOverloads constructor(
    ctx: Context,
    private val textStyle: TextType = TextType.Body1
) : CoreLinearLayout(ctx) {

    private val typefaceManager = (context.applicationContext as App).typefaceManager

    private val editView = AppCompatEditText(context)
    private val errorView = CoreTextView(context, textColor = colorError, textStyle = TextType.Caption2)

    var error: CharSequence = ""
        set(value) {
            errorView.text = value
            errorView.isVisible = value.isNotBlank()
            if (value.isNotBlank()) {
                editBackgroundByColor(themeManager.selected_theme.colorsStyle.color(colorError))
            } else {
                editBackgroundByColor(themeManager.selected_theme.colorsStyle.color(primaryColor))
            }
            field = value
        }

    init {
        orientation = VERTICAL

        editView.id = R.id.edit_view_1
        editView.includeFontPadding = false
        editView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())

        addView(editView)

        errorView.isVisible = false
        errorView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
            .marginTop(context.dp(8)))
        addView(errorView)
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
        val textColor = theme.colorsStyle.color(primaryTextColor)
        editView.setHintTextColor(textColor.withAlpha(0.31f))
        editView.setTextColor(textColor)

        val (fontFamily, textSize) = theme.textStyle.style(textStyle)
        editView.typeface = typefaceManager.typeface(fontFamily)
        editView.fontSize(textSize)

        editBackgroundByColor(theme.colorsStyle.color(primaryColor))
    }

    fun singleLine() {
        editView.isSingleLine = true
    }

    fun changeText(text: String) {
        editView.setText(text)
    }

    fun changeHint(@StringRes hint: Int) {
        editView.setHint(hint)
    }

    fun changeTextListener(listener: (String) -> Unit) {
        editView.doAfterTextChanged { listener.invoke(it.toString()) }
    }

    private fun editBackgroundByColor(color: Int) {
        editView.background = InsetDrawable(
            GradientDrawable().apply { setStroke(context.dp(2), color) },
            -context.dp(2),
            -context.dp(2),
            -context.dp(2),
            0
        )
        editView.padding(horizontal = context.dp(2), vertical = context.dp(8))
    }

}