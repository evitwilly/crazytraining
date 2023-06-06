package ru.freeit.crazytraining.training.adapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreSecondaryLinearLayout
import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseViewHolder(
    view: LinearLayout,
    private val titleView: TextView,
    private val imageView: ImageView
) : RecyclerView.ViewHolder(view) {

    fun bind(model: ExerciseModel) = with(model) {
        bindTitle(titleView)
        bindImage(imageView)
    }

    companion object {
        fun from(parent: ViewGroup) : ExerciseViewHolder {
            val context = parent.context

            val contentLinearView = CoreSecondaryLinearLayout(context)
            contentLinearView.elevation = context.dp(2f)
            contentLinearView.orientation = LinearLayout.VERTICAL
            contentLinearView.padding(context.dp(12))
            contentLinearView.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(8)))

            val headerFrameView = FrameLayout(context)
            headerFrameView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
            contentLinearView.addView(headerFrameView)

            val iconView = ImageView(context)
            iconView.layoutParams(frameLayoutParams().width(context.dp(32)).height(context.dp(32)))
            iconView.padding(context.dp(4))
            headerFrameView.addView(iconView)

            val titleView = CoreTextView(context)
            titleView.includeFontPadding = false
            titleView.fontSize(17f)
            titleView.fontFamily(TypefaceStyle.MEDIUM)
            titleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight().marginStart(context.dp(40))
                .gravity(Gravity.TOP))
            headerFrameView.addView(titleView)

            val button = CoreButton(context)
            button.changeStartIcon(R.drawable.ic_add)
            button.setText(R.string.set)
            button.layoutParams(linearLayoutParams().wrap().gravity(Gravity.END).marginTop(context.dp(8)))
            button.padding(horizontal = context.dp(8), vertical = context.dp(4))
            contentLinearView.addView(button)

            return ExerciseViewHolder(contentLinearView, titleView, iconView)
        }
    }
}