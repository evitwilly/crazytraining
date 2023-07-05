package ru.freeit.crazytraining.exercise.list.adapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.theming.CoreTheme
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes
import ru.freeit.crazytraining.core.theming.colors.ColorAttributes.secondaryBackgroundColor
import ru.freeit.crazytraining.core.theming.corners.ShapeAttribute
import ru.freeit.crazytraining.core.theming.corners.ShapeTreatmentStrategy
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreImageButtonView
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.list.viewmodel_states.ExerciseDetailState

class ExerciseViewHolder(
    view: LinearLayout,
    private val titleView: TextView,
    private val unitView: TextView,
    private val editButtonView: ImageView,
    private val buttonsView: LinearLayout,
) : RecyclerView.ViewHolder(view) {

    fun bind(detailState: ExerciseDetailState) {
        val model = detailState.exerciseModel

        with(model) {
            bindTitle(titleView)
            bindUnit(unitView)
        }

        val observer = Observer<ExerciseEditButtonState> { editButtonState ->
            with(editButtonState) {
                bindImageView(editButtonView)
                bindButtons(buttonsView, model)
            }
        }
        val viewModel = detailState.editButtonViewModel
        editButtonView.setOnClickListener { viewModel.toggle() }
        editButtonView.doOnAttach { viewModel.state.observeForever(observer) }
        editButtonView.doOnDetach { viewModel.state.removeObserver(observer) }

    }

    companion object {
        fun from(parent: ViewGroup) : ExerciseViewHolder {
            val context = parent.context

            val contentLinearView = CoreLinearLayout(context,
                backgroundColor = secondaryBackgroundColor,
                shapeTreatmentStrategy = ShapeTreatmentStrategy.AllRounded()
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

            val editButtonView = CoreImageButtonView(
                ctx = context,
                shape = ShapeAttribute.medium,
                shapeTreatmentStrategy = ShapeTreatmentStrategy.StartBottomTopEndRounded()
            )
            editButtonView.padding(context.dp(8))
            editButtonView.isVisible = false
            editButtonView.layoutParams(frameLayoutParams().width(context.dp(32))
                .height(context.dp(32)).gravity(Gravity.END))
            headerFrameView.addView(editButtonView)

            val buttonsView = CoreLinearLayout(context, ColorAttributes.transparent)
            buttonsView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
                .marginStart(context.dp(12))
                .marginEnd(context.dp(12))
                .marginTop(context.dp(16))
                .marginBottom(context.dp(12)))
            buttonsView.orientation = LinearLayout.HORIZONTAL
            contentLinearView.addView(buttonsView)

            val unitView = CoreTextView(context, textStyle = TextAttribute.Body2)
            unitView.layoutParams(linearLayoutParams().wrap().gravity(Gravity.END)
                .marginEnd(context.dp(12))
                .marginBottom(context.dp(4)))
            contentLinearView.addView(unitView)

            return ExerciseViewHolder(contentLinearView, titleView, unitView, editButtonView, buttonsView)
        }
    }

}