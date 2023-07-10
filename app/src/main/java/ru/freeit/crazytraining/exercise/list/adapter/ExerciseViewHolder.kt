package ru.freeit.crazytraining.exercise.list.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes.*
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.theming.view.CoreImageButtonView
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseDetailState
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseViewHolderListeners(
    val clickListener: (ExerciseModel) -> Unit,
    val removeListener: (ExerciseModel) -> Unit,
    val changeStatusListener: (ExerciseModel, Boolean) -> Unit
)

class ExerciseViewHolder(
    private val contentView: LinearLayout,
    private val titleView: TextView,
    private val unitView: TextView,
    private val removeButtonView: ImageView,
    private val statusButtonView: CoreButton,
) : RecyclerView.ViewHolder(contentView) {

    fun bind(detailState: ExerciseDetailState, listeners: ExerciseViewHolderListeners) {
        val model = detailState.exerciseModel

        with(model) {
            bindTitle(titleView)
            bindUnit(unitView)
        }

        detailState.bindStatus(statusButtonView)

        contentView.setOnClickListener {
            listeners.clickListener.invoke(detailState.exerciseModel)
        }

        removeButtonView.setOnClickListener {
            listeners.removeListener.invoke(detailState.exerciseModel)
        }

        statusButtonView.setOnClickListener {
            listeners.changeStatusListener.invoke(detailState.exerciseModel, detailState.toggled_active)
        }

    }

    companion object {
        fun from(parent: ViewGroup) : ExerciseViewHolder {
            val context = parent.context

            val contentLinearView = CoreLinearLayout(context,
                backgroundColor = secondaryBackgroundColor,
                shapeTreatmentStrategy = ShapeTreatmentStrategy.AllRounded(),
                rippleColor = primaryColor
            )
            contentLinearView.elevation = context.dp(2f)
            contentLinearView.orientation = LinearLayout.VERTICAL
            contentLinearView.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(8)))

            val headerFrameView = FrameLayout(context)
            headerFrameView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
            contentLinearView.addView(headerFrameView)

            val titleView = object: CoreTextView(
                ctx = context,
                textStyle = TextAttribute.Body1
            ) {
                override fun onThemeChanged(theme: CoreTheme) {
                    super.onThemeChanged(theme)
                    fontSize(18f)
                }
            }
            titleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight()
                .marginStart(context.dp(12))
                .marginTop(context.dp(12))
                .marginEnd(context.dp(32)))
            headerFrameView.addView(titleView)

            val removeButtonView = CoreImageButtonView(
                ctx = context,
                shape = ShapeAttribute.medium,
                shapeTreatmentStrategy = ShapeTreatmentStrategy.StartBottomTopEndRounded()
            )
            removeButtonView.padding(context.dp(8))
            removeButtonView.setImageResource(R.drawable.ic_close)
            removeButtonView.layoutParams(frameLayoutParams().width(context.dp(32))
                .height(context.dp(32)).gravity(Gravity.END))
            headerFrameView.addView(removeButtonView)

            val bottomButtonsLinearView = CoreLinearLayout(
                ctx = context,
                backgroundColor = transparent
            )
            bottomButtonsLinearView.orientation = LinearLayout.HORIZONTAL
            bottomButtonsLinearView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
                .marginTop(context.dp(12))
                .marginStart(context.dp(12))
                .marginEnd(context.dp(12))
                .marginBottom(context.dp(8)))
            contentLinearView.addView(bottomButtonsLinearView)

            val statusButtonView = object : CoreButton(
                ctx = context,
                shape = ShapeAttribute.big,
                shapeTreatmentStrategy = ShapeTreatmentStrategy.AllRounded()
            ) {
                override fun onThemeChanged(theme: CoreTheme) {
                    super.onThemeChanged(theme)
                    fontSize(14f)
                }
            }
            statusButtonView.layoutParams(linearLayoutParams().wrap())
            statusButtonView.padding(horizontal = context.dp(8), vertical = context.dp(2))
            bottomButtonsLinearView.addView(statusButtonView)

            val spaceView = View(context)
            spaceView.layoutParams(linearLayoutParams().wrap().weight(1f))
            bottomButtonsLinearView.addView(spaceView)

            val unitView = CoreTextView(context, textStyle = TextAttribute.Body2)
            unitView.layoutParams(linearLayoutParams().wrap())
            bottomButtonsLinearView.addView(unitView)

            return ExerciseViewHolder(contentLinearView, titleView, unitView, removeButtonView, statusButtonView)
        }
    }

}