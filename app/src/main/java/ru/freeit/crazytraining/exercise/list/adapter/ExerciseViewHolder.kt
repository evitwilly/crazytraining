package ru.freeit.crazytraining.exercise.list.adapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.core.theming.colors.ColorType.*
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseViewHolder(
    view: LinearLayout,
    private val titleView: TextView,
    private val imageView: ImageView,
    private val measuredView: TextView
) : RecyclerView.ViewHolder(view) {

    fun bind(model: ExerciseModel) = with(model) {
        bindTitle(titleView)
        bindImage(imageView)
        bindMeasuredValue(measuredView)
    }

    companion object {
        fun from(parent: ViewGroup) : ExerciseViewHolder {
            val context = parent.context

            val contentLinearView = CoreLinearLayout(context,
                backgroundColor = secondaryBackgroundColor,
                cornerTreatmentStrategy = CornerTreatmentStrategy.AllRounded()
            )
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
            titleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight().marginStart(context.dp(40))
                .gravity(Gravity.TOP))
            headerFrameView.addView(titleView)

            val measuredView = CoreTextView(context, textStyle = TextType.Body2)
            measuredView.layoutParams(linearLayoutParams().wrap().gravity(Gravity.END))
            contentLinearView.addView(measuredView)

            return ExerciseViewHolder(contentLinearView, titleView, iconView, measuredView)
        }
    }

}