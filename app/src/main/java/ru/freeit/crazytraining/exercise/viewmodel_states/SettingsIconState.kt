package ru.freeit.crazytraining.exercise.viewmodel_states

import android.graphics.drawable.GradientDrawable
import android.view.View
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.view.CoreImageView

class SettingsIconState(
    private val icons: IntArray,
    private val colors: IntArray
) {

    fun bindIconsView(parent: CoreLinearLayout, selectListener: (Int) -> Unit) {
        parent.removeAllViews()

        val context = parent.context
        icons.forEach { icon ->
            val iconView = CoreImageView(context)
            iconView.setImageResource(icon)
            iconView.setOnClickListener { selectListener.invoke(icon) }
            iconView.padding(context.dp(8))
            iconView.layoutParams(linearLayoutParams().width(context.dp(56)).height(context.dp(56)))
            parent.addView(iconView)
        }
    }

    fun bindColorsView(parent: CoreLinearLayout, selectListener: (Int) -> Unit) {
        parent.removeAllViews()

        val context = parent.context
        colors.forEach { color ->
            val view = View(context)
            view.background = GradientDrawable().apply {
                setColor(color)
            }
            view.setOnClickListener { selectListener.invoke(color) }
            view.layoutParams(linearLayoutParams().width(context.dp(48)).height(context.dp(48)))
            parent.addView(view)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is SettingsIconState) return false
        return icons.contentEquals(other.icons) && colors.contentEquals(other.colors)
    }

}