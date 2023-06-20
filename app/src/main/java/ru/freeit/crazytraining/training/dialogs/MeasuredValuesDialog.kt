package ru.freeit.crazytraining.training.dialogs

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.dialogs.CoreDialog
import ru.freeit.crazytraining.core.theming.colors.ColorType
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextType
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.core.viewmodel.viewModelFactory
import ru.freeit.crazytraining.exercise.detail.model.ExerciseMeasuredValueModel
import ru.freeit.crazytraining.training.dialogs.viewmodel_states.MeasuredValuesState

class MeasuredValuesDialog() : CoreDialog() {

    override val name: String = "MeasuredValuesDialog"

    constructor(model: ExerciseMeasuredValueModel) : this() {
        arguments = bundleOf(argument_key to model.ordinal)
    }

    override fun createView(context: Context): View {
        val contentView = CoreLinearLayout(
            ctx = context,
            cornerRadiusStyle = radius,
            backgroundColor = ColorType.secondaryBackgroundColor
        )
        contentView.padding(horizontal = context.dp(16), vertical = context.dp(12))
        contentView.orientation = LinearLayout.VERTICAL

        val titleView = CoreTextView(context, textStyle = TextType.Body1)
        titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        contentView.addView(titleView)

        val editLayoutView = CoreLinearLayout(context, backgroundColor = ColorType.transparent)
        editLayoutView.gravity = Gravity.BOTTOM
        editLayoutView.orientation = LinearLayout.HORIZONTAL
        editLayoutView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(4)))
        contentView.addView(editLayoutView)

        val errorView = CoreTextView(context, textColor = ColorType.colorError, textStyle = TextType.Caption2)
        errorView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(8)))
        errorView.setText(R.string.both_fields_are_empty)
        errorView.isVisible = false
        contentView.addView(errorView)

        val button = CoreButton(context, cornerTreatmentStrategy = CornerTreatmentStrategy.AllRounded())
        button.setText(R.string.add)
        button.padding(horizontal = context.dp(24), vertical = context.dp(4))
        button.layoutParams(linearLayoutParams().wrap().gravity(Gravity.END).marginTop(context.dp(4)))
        contentView.addView(button)

        val argument = ExerciseMeasuredValueModel.values()[requireArguments().getInt(argument_key)]
        val factory = viewModelFactory { MeasuredValuesViewModel(argument) }
        val viewModel = ViewModelProvider(this, factory)[MeasuredValuesViewModel::class.java]

        viewModel.measuredValuesState.observe(viewLifecycleOwner) { state ->
            state.bindViews(
                titleView = titleView,
                editLayoutView = editLayoutView,
                amountListener = { amount ->
                    errorView.isVisible = amount <= 0 && (state is MeasuredValuesState.Distance || state is MeasuredValuesState.Time)
                    viewModel.cacheAmount(amount)
                }
            )
        }

        button.setOnClickListener { viewModel.apply() }

        val fragmentResult = MeasuredValuesDialogResult(parentFragmentManager)
        viewModel.amountState.observe(viewLifecycleOwner) { amount ->
            fragmentResult.result(amount)
            dismiss()
        }

        return contentView
    }

    companion object {
        private const val argument_key = "ExerciseMeasuredValueModel_key"
    }

}