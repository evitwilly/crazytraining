package ru.freeit.crazytraining.exercise.detail.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.recyclerLayoutParams
import ru.freeit.crazytraining.core.theming.view.CoreImageView
import ru.freeit.crazytraining.core.theming.adapter.CoreViewHolder
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.padding

class ExerciseIconViewHolder(
    private val imageView: CoreImageView,
    private val selectListener: (Int) -> Unit,
    private val checkedIcon: Int
) : CoreViewHolder<Int>(imageView) {

    override fun bind(item: Int) {
        imageView.setImageResource(item)
        imageView.background = if (item == checkedIcon) {
            val theme = imageView.themeManager.selected_theme
            GradientDrawable().apply {
                cornerRadius = theme.cornerRadiusStyle.style(imageView.context, CornerRadiusType.medium)
                setStroke(
                    imageView.context.dp(2),
                    theme.colorsStyle.color(ColorType.primaryColor)
                )
            }
        } else {
            GradientDrawable()
        }
        imageView.setOnClickListener { selectListener.invoke(item) }
    }

    companion object {
        fun from(context: Context, selectListener: (Int) -> Unit, checkedIcon: Int): ExerciseIconViewHolder {
            val imageView = object : CoreImageView(context) {
                override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                    super.onMeasure(widthMeasureSpec, widthMeasureSpec)
                }
            }
            imageView.padding(context.dp(8))
            imageView.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight())
            imageView.isClickable = true
            imageView.isFocusable = true
            return ExerciseIconViewHolder(imageView, selectListener, checkedIcon)
        }
    }

}