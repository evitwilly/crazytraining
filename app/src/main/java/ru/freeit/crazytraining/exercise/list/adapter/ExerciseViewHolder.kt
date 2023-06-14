package ru.freeit.crazytraining.exercise.list.adapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.colors.ColorType.secondaryBackgroundColor
import ru.freeit.crazytraining.core.theming.corners.CornerRadiusType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreImageButtonView
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.model.ExerciseModel

class ExerciseViewHolder(
    view: LinearLayout,
    private val titleView: TextView,
    private val imageView: ImageView,
    private val measuredView: TextView,
    private val editButtonView: ImageView,
    private val buttonsView: LinearLayout,
    private val viewModel: ExerciseEditButtonViewModel
) : RecyclerView.ViewHolder(view) {

    fun bind(model: ExerciseModel) = with(model) {
        bindTitle(titleView)
        bindImage(imageView)
        bindMeasuredValue(measuredView)

        val observer = Observer<ExerciseEditButtonState> { state ->
            with(state) {
                bindImageView(editButtonView)
                bindButtons(buttonsView, model)
            }
        }
        editButtonView.setOnClickListener { viewModel.toggle() }
        editButtonView.doOnAttach { viewModel.state.observeForever(observer) }
        editButtonView.doOnDetach { viewModel.state.removeObserver(observer) }

    }

    companion object {
        fun from(parent: ViewGroup, viewModel: ExerciseEditButtonViewModel) : ExerciseViewHolder {
            val context = parent.context

            val contentLinearView = CoreLinearLayout(context,
                backgroundColor = secondaryBackgroundColor,
                cornerTreatmentStrategy = CornerTreatmentStrategy.AllRounded()
            )
            contentLinearView.elevation = context.dp(2f)
            contentLinearView.padding(bottom = context.dp(12))
            contentLinearView.orientation = LinearLayout.VERTICAL
            contentLinearView.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(8)))

            val headerFrameView = FrameLayout(context)
            headerFrameView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
            contentLinearView.addView(headerFrameView)

            val iconView = ImageView(context)
            iconView.layoutParams(frameLayoutParams().width(context.dp(32)).height(context.dp(32))
                .marginStart(context.dp(12)).marginTop(context.dp(12)))
            iconView.padding(context.dp(4))
            headerFrameView.addView(iconView)

            val titleView = CoreTextView(context)
            titleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight()
                .marginStart(context.dp(52))
                .marginTop(context.dp(12))
                .gravity(Gravity.TOP))
            headerFrameView.addView(titleView)

            val editButtonView = CoreImageButtonView(
                ctx = context,
                cornerRadiusType = CornerRadiusType.medium,
                cornerTreatmentStrategy = CornerTreatmentStrategy.StartBottomTopEndRounded()
            )
            editButtonView.padding(context.dp(8))
            editButtonView.setOnClickListener { editButtonView.showContextMenu() }
            editButtonView.layoutParams(frameLayoutParams().width(context.dp(32))
                .height(context.dp(32)).gravity(Gravity.END))
            headerFrameView.addView(editButtonView)

            val measuredView = CoreTextView(context, textStyle = TextType.Body2)
            measuredView.layoutParams(linearLayoutParams().wrap().gravity(Gravity.END)
                .marginEnd(context.dp(12)))
            contentLinearView.addView(measuredView)

            val buttonsView = CoreLinearLayout(context, ColorType.transparent)
            buttonsView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
                .marginStart(context.dp(12))
                .marginEnd(context.dp(12)))
            buttonsView.orientation = LinearLayout.HORIZONTAL
            contentLinearView.addView(buttonsView)

            return ExerciseViewHolder(contentLinearView, titleView, iconView, measuredView, editButtonView, buttonsView, viewModel)
        }
    }

}