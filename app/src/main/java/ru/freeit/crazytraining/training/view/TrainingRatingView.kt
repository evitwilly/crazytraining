package ru.freeit.crazytraining.training.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.view.CoreTextView

class TrainingRatingView(ctx: Context) : CoreLinearLayout(ctx) {

    private var rating: Int = 4
    private val views = mutableListOf<RatingTextView>()

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_HORIZONTAL

        views.addAll(List(max_rating) { index ->
            val textView = RatingTextView(context)
            val currentRating = index + 1
            textView.text = "$currentRating"
            textView.hasSelected = index < rating
            textView.setOnClickListener { changeRating(index + 1) }
            textView.layoutParams(
                linearLayoutParams().width(context.dp(40))
                .marginEnd(if (currentRating < max_rating) context.dp(horizontal_margin) else 0))
            textView
        })

        addView(*views.toTypedArray())
    }

    fun changeRating(rating: Int) {
        this.rating = rating
        drawState()
    }

    private fun drawState() {
        views.forEachIndexed { index, view ->
            view.hasSelected = index < rating
        }
    }

    private class RatingTextView(ctx: Context): CoreTextView(ctx) {

        var hasSelected: Boolean = false
            set(value) {
                field = value
                drawState(themeManager.selected_theme)
            }

        init {
            gravity = Gravity.CENTER
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        }

        override fun onThemeChanged(theme: CoreTheme) {
            super.onThemeChanged(theme)
            fontSize(18f)
            drawState(theme)
        }

        private fun drawState(theme: CoreTheme) {
            background = if (hasSelected) {
                setTextColor(theme.colors[ColorAttributes.colorOnPrimary])

                GradientDrawable().apply {
                    cornerRadius = context.dp(theme.shapeStyle[ShapeAttribute.small])
                    setColor(theme.colors[ColorAttributes.primaryColor])
                }
            } else {
                setTextColor(theme.colors[ColorAttributes.primaryTextColor])

                GradientDrawable().apply {
                    cornerRadius = context.dp(theme.shapeStyle[ShapeAttribute.small])
                    setStroke(
                        context.dp(2),
                        theme.colors[ColorAttributes.primaryColor]
                    )
                }
            }
        }

    }

    private companion object {
        const val max_rating = 5
        const val horizontal_margin = 16
    }

}