package ru.freeit.crazytraining.training.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.fontSize
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class TrainingDateTextView(ctx: Context) : CoreTextView(ctx) {

    private val lineStrokeWidth = context.dp(4f)
    private val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = lineStrokeWidth
    }

    init {
        padding(bottom = context.dp(4), end = context.dp(4))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        greenPaint.style = Paint.Style.STROKE

        canvas.drawLine(0f, height - lineStrokeWidth, width.toFloat(), height - lineStrokeWidth, greenPaint)

    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)
        greenPaint.color = theme.colorsStyle.color(primaryColor)
        typeface = typefaceManager.typeface(theme.trainingDateTitleStyle)
        fontSize(theme.trainingDateTitleSize)
    }

}