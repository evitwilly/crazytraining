package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute

class CoreEditText @JvmOverloads constructor(
    ctx: Context,
    private val textStyle: TextAttribute = TextAttribute.Body1,
    private val horizontalPadding: Int = 2,
    private val verticalPadding: Int = 8
) : CoreLinearLayout(ctx) {

    private val typefaceManager = (context.applicationContext as App).typefaceManager

    private val editView = AppCompatEditText(context)
    private val errorView = CoreTextView(context, textColor = ColorAttributes.colorError, textStyle = TextAttribute.Caption2)

    val text: String
        get() = editView.text.toString()

    var error: Error = Error.Empty
        set(value) {
            when (value) {
                is Error.Field -> {
                    editBackgroundByColor(themeManager.selected_theme.colors[ColorAttributes.colorError])
                    errorView.isVisible = false
                }
                is Error.Text -> {
                    errorView.isVisible = true
                    errorView.text = value.error
                    editBackgroundByColor(themeManager.selected_theme.colors[ColorAttributes.colorError])
                }
                is Error.Empty -> {
                    errorView.text = ""
                    errorView.isVisible = false
                    editBackgroundByColor(themeManager.selected_theme.colors[ColorAttributes.primaryColor])
                }
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
        errorView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(8)))
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
        val textColor = theme.colors[ColorAttributes.primaryTextColor]
        editView.setHintTextColor(textColor.withAlpha(0.31f))
        editView.setTextColor(textColor)

        val (fontFamily, textSize) = theme.textStyle[textStyle]
        editView.typeface = typefaceManager.typeface(fontFamily)
        editView.fontSize(textSize)

        editBackgroundByColor(theme.colors[ColorAttributes.primaryColor])
    }

    fun changeMaxLength(maxLength: Int) {
        val filters = editView.filters ?: arrayOf()
        editView.filters = filters + arrayOf(InputFilter.LengthFilter(maxLength))
    }

    fun changeLines(lines: Int) {
        editView.setLines(lines)
        editView.maxLines = lines
        editView.gravity = Gravity.START or Gravity.TOP
    }

    fun singleLine() {
        editView.isSingleLine = true
    }

    fun numbered() {
        editView.inputType = InputType.TYPE_CLASS_NUMBER
    }

    fun changeText(text: String) {
        editView.setText(text)
    }

    fun changeHint(text: String) {
        editView.hint = text
    }

    fun addTextWatcher(watcher: TextWatcher) {
        editView.addTextChangedListener(watcher)
    }

    fun removeTextWatcher(watcher: TextWatcher) {
        editView.removeTextChangedListener(watcher)
    }

    fun moveCursorToEnd() {
        val string = editView.text.toString()
        if (string.isNotEmpty()) {
            editView.setSelection(string.length)
        }
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
        editView.padding(horizontal = context.dp(horizontalPadding), vertical = context.dp(verticalPadding))
    }

    sealed interface Error {

        object Empty : Error

        object Field : Error

        class Text(val error: String) : Error

    }

}