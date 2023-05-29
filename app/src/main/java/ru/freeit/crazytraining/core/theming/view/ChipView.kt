package ru.freeit.crazytraining.core.theming.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.view.View
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.fontSizeInPixels
import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle

class ChipView(ctx: Context): View(ctx) {

    private val themeManager = (context.applicationContext as App).themeManager
    private val typefaceManager = (context.applicationContext as App).typefaceManager

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        val chipTextSize = context.fontSizeInPixels(theme.chipTextSize)
        val chipTextStyle = typefaceManager.typeface(theme.chipTextStyle)

        selectedPaint.color = theme.chipSelectedColor
        selectedTextPaint.color = theme.chipSelectedTextColor
        selectedTextPaint.textSize = chipTextSize
        selectedTextPaint.typeface = chipTextStyle
        unselectedPaint.color = theme.chipUnselectedColor
        unselectedTextPaint.color = theme.chipUnselectedColor
        unselectedTextPaint.textSize = chipTextSize
        unselectedTextPaint.typeface = chipTextStyle
        invalidate()
    }

    private val selectedTextPaint = TextPaint().apply {
        typeface = typefaceManager.typeface(TypefaceStyle.MEDIUM)
    }

    private val unselectedTextPaint = TextPaint().apply {
        typeface = typefaceManager.typeface(TypefaceStyle.MEDIUM)
    }

    private val cornerRadius = context.dp(50f)
    private val viewStrokeWidth = context.dp(1f)
    private val unselectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = viewStrokeWidth
        style = Paint.Style.STROKE
        color = CoreColors.greenMedium
    }

    private val selectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = viewStrokeWidth
        style = Paint.Style.FILL
    }

    var text = ""
        set(value) {
            field = value
            invalidate()
        }

    var checked = false
        set(value) {
            field = value
            invalidate()
        }

    private var checkedListener: (Boolean) -> Unit = {}

    init {
        isClickable = true
        isFocusable = true
        setOnClickListener {
            checked = !checked
            checkedListener.invoke(checked)
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val width = unselectedTextPaint.measureText(text).toInt()
        val fontMetrics = unselectedTextPaint.fontMetrics
        val height = (fontMetrics.descent - fontMetrics.ascent).toInt()

        val desiredWidth = paddingStart + width + paddingEnd
        val desiredHeight = paddingTop + height + paddingBottom

        val resolvedWidth = resolveSize(desiredWidth, widthMeasureSpec)
        val resolvedHeight = resolveSize(desiredHeight, heightMeasureSpec)

        setMeasuredDimension(resolvedWidth, resolvedHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val textWidth = unselectedTextPaint.measureText(text)
        val fontMetrics = unselectedTextPaint.fontMetrics
        val textHeight = (fontMetrics.descent + fontMetrics.ascent) / 2

        val paint = if (checked) selectedPaint else unselectedPaint
        canvas.drawRoundRect(viewStrokeWidth, viewStrokeWidth, width - viewStrokeWidth, height - viewStrokeWidth, cornerRadius, cornerRadius, paint)
        val textPaint = if (checked) selectedTextPaint else unselectedTextPaint
        canvas.drawText(text, width / 2f - textWidth / 2f, height / 2f - textHeight, textPaint)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(onThemeChanged)
    }

    fun changeCheckedListener(listener: (Boolean) -> Unit) {
        checkedListener = listener
    }

}