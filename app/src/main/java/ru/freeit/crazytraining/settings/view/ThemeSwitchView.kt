package ru.freeit.crazytraining.settings.view

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.CoreColors
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.extensions.bitmapByResource
import ru.freeit.crazytraining.core.extensions.dp

class ThemeSwitchView(ctx: Context): View(ctx) {

    private val cornerRadius = context.dp(8f)
    private val viewStrokeWidth = context.dp(2f)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = CoreColors.greenMedium
        style = Paint.Style.STROKE
        strokeWidth = viewStrokeWidth
    }
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = CoreColors.greenMedium
        style = Paint.Style.FILL
    }

    private val selectedBitmapPaint = Paint().apply {
        colorFilter = PorterDuffColorFilter(CoreColors.white, PorterDuff.Mode.SRC_IN)
    }

    private val unselectedBitmapPaint = Paint().apply {
        colorFilter = PorterDuffColorFilter(CoreColors.greenMedium, PorterDuff.Mode.SRC_IN)
    }

    private val iconSize = context.dp(32)

    private val selectedIconPath = Path()

    private val lightBitmap = context.bitmapByResource(R.drawable.ic_light_theme, iconSize)
    private val darkBitmap = context.bitmapByResource(R.drawable.ic_dark_theme, iconSize)

    private var isLightSelected: Boolean = (context.applicationContext as App).themeManager.selected_theme == CoreTheme.LIGHT
    private var themeSelectListener: (CoreTheme) -> Unit = {}

    init {
        isClickable = true
        isFocusable = true
        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                isLightSelected = event.x < width / 2
                invalidate()
                performClick()
                themeSelectListener.invoke(if (isLightSelected) CoreTheme.LIGHT else CoreTheme.DARK)
            }
            true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawLine(width / 2f - viewStrokeWidth / 2, viewStrokeWidth, width / 2f - viewStrokeWidth / 2, height - viewStrokeWidth, strokePaint)
        canvas.drawRoundRect(viewStrokeWidth, viewStrokeWidth, width - viewStrokeWidth, height - viewStrokeWidth, cornerRadius, cornerRadius, strokePaint)

        selectedIconPath.reset()
        if (isLightSelected) {
            selectedIconPath.addRoundRect(
                viewStrokeWidth,
                viewStrokeWidth,
                width / 2f,
                height - viewStrokeWidth,
                floatArrayOf(cornerRadius, cornerRadius, 0f, 0f, 0f, 0f, cornerRadius, cornerRadius),
                Path.Direction.CW
            )
        } else {
            selectedIconPath.addRoundRect(
                width / 2f,
                viewStrokeWidth,
                width - viewStrokeWidth,
                height - viewStrokeWidth,
                floatArrayOf(0f, 0f, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f),
                Path.Direction.CW
            )
        }

        canvas.drawPath(selectedIconPath, fillPaint)

        val lightBitmapPaint = if (isLightSelected) selectedBitmapPaint else unselectedBitmapPaint
        canvas.drawBitmap(lightBitmap, width / 4f - iconSize / 2, height / 2f - iconSize / 2, lightBitmapPaint)

        val darkBitmapPaint = if (!isLightSelected) selectedBitmapPaint else unselectedBitmapPaint
        canvas.drawBitmap(darkBitmap, (width / 2f + width / 4f) - iconSize / 2, height / 2f - iconSize / 2, darkBitmapPaint)

    }

    fun changeThemeSelectListener(listener: (CoreTheme) -> Unit) {
        themeSelectListener = listener
    }

}