package ru.freeit.crazytraining.training.adapter

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.ResourcesProviderImpl
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.theming.CoreTheme
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
        val resources = ResourcesProviderImpl(context.resources)
        sets.forEach { (model, number) ->

            val exerciseSetView = ExerciseSetView(context)
            exerciseSetView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(4)))
            exerciseSetsLayoutView.addView(exerciseSetView)

            exerciseSetView.changeTitle(resources.string(
                R.string.set_title,
                resources.quantityString(R.plurals.set, number, number),
                model.amountString(resources)
            ))

            exerciseSetView.changePlusButtonImageResource(R.drawable.ic_add)
            exerciseSetView.changePlusButtonClickListener { listeners.plusListener.invoke(model) }

            exerciseSetView.changeRemoveButtonImageResource(if (number > 1) R.drawable.ic_minus else R.drawable.ic_close)
            exerciseSetView.changeRemoveButtonClickListener {
                if (number > 1) {
                    listeners.minusListener.invoke(model)
                } else {
                    listeners.removeListener.invoke(model)
                }
            }

        }

        val totalAmountSet = state.model_with_total_amount
        val totalAmountString = totalAmountSet.amountString(resources)
        totalView.text = if (totalAmountSet.isNotEmpty) {
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
                .marginEnd(context.dp(12)))
            contentLinearView.addView(titleView)

            val exerciseSetsLayoutView = CoreLinearLayout(context, backgroundColor = ColorAttributes.transparent)
            exerciseSetsLayoutView.orientation = LinearLayout.VERTICAL
            exerciseSetsLayoutView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
                .marginStart(context.dp(16))
                .marginEnd(context.dp(12))
                .marginTop(context.dp(16)))
            contentLinearView.addView(exerciseSetsLayoutView)

            val bottomLinearView = CoreLinearLayout(context, backgroundColor = ColorAttributes.transparent)
            bottomLinearView.orientation = LinearLayout.HORIZONTAL
            bottomLinearView.gravity = Gravity.CENTER_VERTICAL
            bottomLinearView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(16))
                .marginStart(context.dp(12)).marginEnd(context.dp(12)))
            contentLinearView.addView(bottomLinearView)

            val totalView = CoreTextView(context, textStyle = TextAttribute.Caption2)
            totalView.layoutParams(linearLayoutParams().wrap().weight(1f)
                .marginEnd(context.dp(12)))
            bottomLinearView.addView(totalView)

            val buttonView = CoreButton(
                ctx = context,
                shapeTreatmentStrategy = ShapeTreatmentStrategy.AllElliptical()
            )
            buttonView.padding(
                start = context.dp(8),
                top = context.dp(2),
                end = context.dp(12),
                bottom = context.dp(2)
            )
            buttonView.changeStartIcon(R.drawable.ic_add, 16)
            buttonView.setText(R.string.set)
            buttonView.layoutParams(linearLayoutParams().wrap())
            bottomLinearView.addView(buttonView)

            return TrainingViewHolder(contentLinearView, exerciseSetsLayoutView, titleView, totalView, buttonView)
        }
    }

    class ExerciseSetView(ctx: Context) : CoreFrameLayout(ctx, backgroundColor = ColorAttributes.transparent) {

        private val buttonSize = context.dp(32)
        private val buttonMargin = context.dp(1)

        private val titleView = object : CoreTextView(context, textStyle = TextAttribute.Body2) {
            override fun onThemeChanged(theme: CoreTheme) {
                super.onThemeChanged(theme)
                fontSize(14f)
            }
        }

        private val plusButton = newButton(
            shapeTreatmentStrategy = ShapeTreatmentStrategy.StartElliptical(),
            marginEnd = buttonSize + buttonMargin
        )

        private val removeButton = newButton(
            shapeTreatmentStrategy = ShapeTreatmentStrategy.EndElliptical(),
            marginEnd = 0
        )

        init {
            padding(context.dp(4))

            titleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight()
                .gravity(Gravity.CENTER_VERTICAL)
                .marginEnd(buttonSize * 2 + buttonMargin * 2))
            addView(titleView)

            addView(plusButton)

            addView(removeButton)
        }

        fun changeTitle(string: String) {
            titleView.text = string
        }

        fun changePlusButtonImageResource(drawableResource: Int) {
            plusButton.setImageResource(drawableResource)
        }

        fun changeRemoveButtonImageResource(drawableResource: Int) {
            removeButton.setImageResource(drawableResource)
        }

        fun changePlusButtonClickListener(listener: OnClickListener) {
            plusButton.setOnClickListener(listener)
        }

        fun changeRemoveButtonClickListener(listener: OnClickListener) {
            removeButton.setOnClickListener(listener)
        }

        private fun newButton(shapeTreatmentStrategy: ShapeTreatmentStrategy, marginEnd: Int): CoreImageButtonView {
            val button = CoreImageButtonView(
                ctx = context,
                backgroundColor = ColorAttributes.primaryColor,
                rippleColor = ColorAttributes.primaryDarkColor,
                shapeTreatmentStrategy = shapeTreatmentStrategy,
                tintColor = ColorAttributes.colorOnPrimary
            )
            button.padding(context.dp(2))
            button.layoutParams(frameLayoutParams().width(buttonSize).height(context.dp(20))
                .marginEnd(marginEnd)
                .gravity(Gravity.END or Gravity.CENTER_VERTICAL))
            return button
        }

    }

}