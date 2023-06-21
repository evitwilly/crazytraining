package ru.freeit.crazytraining.training.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.model.ExerciseModel
import ru.freeit.crazytraining.exercise.model.ExerciseSetModel
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailState

class TrainingViewHolder(
    view: View,
    private val exerciseSetsLayoutView: CoreLinearLayout,
    private val iconView: ImageView,
    private val titleView: CoreTextView,
    private val buttonView: CoreButton
) : RecyclerView.ViewHolder(view) {

    fun bind(state: TrainingDetailState, addListener: (ExerciseModel) -> Unit, removeListener: (ExerciseSetModel) -> Unit) {
        with(state.model) {
            bindImage(iconView)
            bindTitle(titleView)
        }

        state.bindSetsViews(exerciseSetsLayoutView, removeListener)

        buttonView.setOnClickListener { addListener.invoke(state.model) }
    }

    companion object {
        fun from(parent: ViewGroup) : TrainingViewHolder {
            val context = parent.context
            val contentLinearView = CoreLinearLayout(
                context,
                backgroundColor = ColorType.secondaryBackgroundColor,
                cornerTreatmentStrategy = CornerTreatmentStrategy.AllRounded()
            )
            contentLinearView.elevation = context.dp(2f)
            contentLinearView.orientation = LinearLayout.VERTICAL
            contentLinearView.padding(bottom = context.dp(12))
            contentLinearView.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(8)))

            val headerFrameView = FrameLayout(context)
            headerFrameView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
            contentLinearView.addView(headerFrameView)

            val iconView = ImageView(context)
            iconView.layoutParams(frameLayoutParams().width(context.dp(32)).height(context.dp(32))
                .marginStart(context.dp(12))
                .marginTop(context.dp(12)))
            iconView.padding(context.dp(4))
            headerFrameView.addView(iconView)

            val titleView = CoreTextView(context)
            titleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight()
                .marginStart(context.dp(52))
                .marginTop(context.dp(12))
                .gravity(Gravity.TOP))
            headerFrameView.addView(titleView)

            val exerciseSetsLayoutView = CoreLinearLayout(context, backgroundColor = ColorType.transparent)
            exerciseSetsLayoutView.orientation = LinearLayout.VERTICAL
            exerciseSetsLayoutView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
                .marginStart(context.dp(12)).marginEnd(context.dp(12)).marginTop(context.dp(8)))
            contentLinearView.addView(exerciseSetsLayoutView)

            val buttonView = CoreButton(context)
            buttonView.padding(horizontal = context.dp(8), vertical = context.dp(2))
            buttonView.changeStartIcon(R.drawable.ic_add, 16)
            buttonView.setText(R.string.set)
            buttonView.layoutParams(linearLayoutParams().wrap().gravity(Gravity.END)
                .marginTop(context.dp(8))
                .marginEnd(context.dp(12)))
            contentLinearView.addView(buttonView)

            return TrainingViewHolder(contentLinearView, exerciseSetsLayoutView, iconView, titleView, buttonView)
        }
    }

}