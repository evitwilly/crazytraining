package ru.freeit.crazytraining.exercise.detail.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.extensions.recyclerLayoutParams
import ru.freeit.crazytraining.core.theming.adapter.CoreViewHolder
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.extensions.dp

class ExerciseColorViewHolder(
    private val view: View,
    private val selectListener: (Int) -> Unit,
    private val checkedColor: Int
) : CoreViewHolder<Int>(view) {

    override fun bind(item: Int) {
        val theme = (view.context.applicationContext as App).themeManager.selected_theme
        view.background = GradientDrawable().apply {
            if (item == checkedColor) {
                setStroke(
                    view.context.dp(2),
                    theme.colorsStyle.color(ColorType.primaryColor)
                )
            }
            setColor(item)
        }
        view.setOnClickListener { selectListener.invoke(item) }
    }

    companion object {
        fun from(context: Context, selectListener: (Int) -> Unit, checkedColor: Int): ExerciseColorViewHolder {
            val view = object : View(context) {
                override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                    super.onMeasure(widthMeasureSpec, widthMeasureSpec)
                }
            }
            view.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight())
            view.isClickable = true
            view.isFocusable = true
            return ExerciseColorViewHolder(view, selectListener, checkedColor)
        }
    }

}