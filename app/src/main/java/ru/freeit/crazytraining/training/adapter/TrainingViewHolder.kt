package ru.freeit.crazytraining.training.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.theming.view.CoreImageButtonView
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailStateListeners

class TrainingViewHolder(
    view: View,
    private val exerciseSetsLayoutView: CoreLinearLayout,
    private val titleView: CoreTextView,
    private val totalView: TextView,
    private val buttonView: CoreButton
) : RecyclerView.ViewHolder(view) {

    fun bind(state: TrainingDetailState, listeners: TrainingDetailStateListeners) {
        state.model.bindTitle(titleView)

        val sets = state.sorted_sets_by_number

        exerciseSetsLayoutView.removeAllViews()
        exerciseSetsLayoutView.isVisible = sets.isNotEmpty()

        val context = exerciseSetsLayoutView.context
        sets.forEach { (model, number) ->

            val layoutView = CoreFrameLayout(context, backgroundColor = ColorAttributes.transparent)
            layoutView.padding(context.dp(4))
            layoutView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(4)))
            exerciseSetsLayoutView.addView(layoutView)

            val buttonSize = context.dp(24)
            val buttonMargin = context.dp(8)

            val titleView = CoreTextView(context, textStyle = TextAttribute.Body2)
            titleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight()
                .gravity(Gravity.CENTER_VERTICAL)
                .marginEnd(buttonSize * 2 + buttonMargin * 2))
            layoutView.addView(titleView)

            val resources = context.resources
            titleView.text = resources.getString(
                R.string.set_title,
                resources.getQuantityString(R.plurals.set, number, number),
                model.amountString(resources)
            )

            val plusButton = CoreImageButtonView(context)
            plusButton.setImageResource(R.drawable.ic_add)
            plusButton.padding(context.dp(4))
            plusButton.setOnClickListener { listeners.plusListener.invoke(model) }
            plusButton.layoutParams(frameLayoutParams().width(buttonSize).height(buttonSize)
                .marginEnd(buttonSize + buttonMargin)
                .gravity(Gravity.END or Gravity.CENTER_VERTICAL))
            layoutView.addView(plusButton)

            val removeButton = CoreImageButtonView(context)
            removeButton.setImageResource(if (number > 1) R.drawable.ic_minus else R.drawable.ic_close)
            removeButton.padding(context.dp(4))
            removeButton.layoutParams(frameLayoutParams().width(buttonSize).height(buttonSize)
                .gravity(Gravity.END or Gravity.CENTER_VERTICAL))
            removeButton.setOnClickListener {
                if (number > 1) {
                    listeners.minusListener.invoke(model)
                } else {
                    listeners.removeListener.invoke(model)
                }
            }
            layoutView.addView(removeButton)

        }

        val setWithTotalAmount = state.model_with_total_amount
        val totalAmountString = setWithTotalAmount.amountString(context.resources)
        totalView.text = if (setWithTotalAmount.isNotEmpty) {
            context.getString(R.string.total_colon, totalAmountString)
        } else {
            ""
        }
        buttonView.setOnClickListener { listeners.addListener.invoke(state.model) }
    }

    companion object {
        fun from(parent: ViewGroup) : TrainingViewHolder {
            val context = parent.context
            val contentLinearView = CoreLinearLayout(
                context,
                backgroundColor = ColorAttributes.secondaryBackgroundColor,
                shapeTreatmentStrategy = ShapeTreatmentStrategy.AllRounded()
            )
            contentLinearView.elevation = context.dp(2f)
            contentLinearView.orientation = LinearLayout.VERTICAL
            contentLinearView.padding(bottom = context.dp(12))
            contentLinearView.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(8)))

            val titleView = CoreTextView(context)
            titleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight()
                .marginStart(context.dp(12))
                .marginTop(context.dp(12))
                .marginEnd(context.dp(12)))
            contentLinearView.addView(titleView)

            val exerciseSetsLayoutView = CoreLinearLayout(context, backgroundColor = ColorAttributes.transparent)
            exerciseSetsLayoutView.orientation = LinearLayout.VERTICAL
            exerciseSetsLayoutView.layoutParams(
                linearLayoutParams().matchWidth().wrapHeight()
                .marginStart(context.dp(12)).marginEnd(context.dp(12)).marginTop(context.dp(8)))
            contentLinearView.addView(exerciseSetsLayoutView)

            val bottomLinearView = CoreLinearLayout(context, backgroundColor = ColorAttributes.transparent)
            bottomLinearView.orientation = LinearLayout.HORIZONTAL
            bottomLinearView.gravity = Gravity.CENTER_VERTICAL
            bottomLinearView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(8))
                .marginStart(context.dp(12)).marginEnd(context.dp(12)))
            contentLinearView.addView(bottomLinearView)

            val totalView = CoreTextView(context, textStyle = TextAttribute.Caption2)
            totalView.layoutParams(linearLayoutParams().wrap().weight(1f)
                .marginEnd(context.dp(12)))
            bottomLinearView.addView(totalView)

            val buttonView = CoreButton(context)
            buttonView.padding(horizontal = context.dp(8), vertical = context.dp(2))
            buttonView.changeStartIcon(R.drawable.ic_add, 16)
            buttonView.setText(R.string.set)
            buttonView.layoutParams(linearLayoutParams().wrap())
            bottomLinearView.addView(buttonView)

            return TrainingViewHolder(contentLinearView, exerciseSetsLayoutView, titleView, totalView, buttonView)
        }
    }

}