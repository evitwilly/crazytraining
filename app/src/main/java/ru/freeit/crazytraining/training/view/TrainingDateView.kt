package ru.freeit.crazytraining.training.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.text.TextAttribute.Title1
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class TrainingDateView(ctx: Context) : CoreTextView(ctx, textStyle = Title1) {

    private val lineStrokeWidth = context.dp(4f)
    private val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineStrokeWidth
    }

    init {
        padding(bottom = context.dp(8), end = context.dp(4))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(0f, height - lineStrokeWidth, width.toFloat(), height - lineStrokeWidth, greenPaint)
    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)
        greenPaint.color = theme.colors[ColorAttributes.primaryColor]
    }

}