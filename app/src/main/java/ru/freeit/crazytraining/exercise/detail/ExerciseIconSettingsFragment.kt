package ru.freeit.crazytraining.exercise.detail

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.navigation.fragment.BaseFragment
import ru.freeit.crazytraining.core.theming.corners.CornerTreatmentStrategy
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.exercise.detail.repository.ExerciseResourcesRepositoryImpl
import ru.freeit.crazytraining.exercise.detail.viewmodel_states.ExerciseIconSettingsListState

class ExerciseIconSettingsFragment() : BaseFragment<ExerciseIconSettingsViewModel>() {

    override val viewModelKClass = ExerciseIconSettingsViewModel::class.java
    override fun viewModelConstructor(ctx: Context, bundle: Bundle?): ExerciseIconSettingsViewModel {
        val arguments = requireArguments()
        return ExerciseIconSettingsViewModel(
            ExerciseResourcesRepositoryImpl(),
            arguments.getInt(checked_icon_arg),
            arguments.getInt(checked_color_arg)
        )
    }

    constructor(checkedColor: Int, checkedIcon: Int): this() {
        arguments = bundleOf(
            checked_color_arg to checkedColor,
            checked_icon_arg to checkedIcon
        )
    }

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL

        changeMenuButtonDrawableResource(R.drawable.ic_check)
        changeMenuButtonVisible(true)
        changeTitle(getString(R.string.icon_settings))

        val iconFrameView = CoreFrameLayout(context)
        iconFrameView.padding(start = context.dp(16), end = context.dp(16))
        iconFrameView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(8)))
        contentView.addView(iconFrameView)

        val iconImageView = AppCompatImageView(context)
        iconImageView.layoutParams(frameLayoutParams().width(context.dp(72)).height(context.dp(72)))
        iconFrameView.addView(iconImageView)

        val toggleButtonView = CoreButton(context, cornerTreatmentStrategy = CornerTreatmentStrategy.AllRounded())
        toggleButtonView.layoutParams(frameLayoutParams().wrap().marginStart(context.dp(88)))
        toggleButtonView.padding(horizontal = context.dp(24), vertical = context.dp(2))
        toggleButtonView.setOnClickListener { viewModel.toggle() }
        iconFrameView.addView(toggleButtonView)

        val listView = RecyclerView(context)
        listView.clipToPadding = false
        listView.padding(context.dp(16))
        listView.layoutManager = GridLayoutManager(context, 5)
        listView.layoutParams(linearLayoutParams().matchWidth().height(0).weight(1f))
        contentView.addView(listView)

        viewModel.listState.observe(viewLifecycleOwner) { state ->
            with(state) {
                bindImageView(iconImageView)
                bindTextView(toggleButtonView)
                if (state is ExerciseIconSettingsListState.Icons) {
                    bindItems(listView, viewModel::checkIcon)
                } else {
                    bindItems(listView, viewModel::checkColor)
                }
            }
        }

        val fragmentResult = ExerciseIconSettingsFragmentResult(parentFragmentManager)
        fun apply() {
            fragmentResult.result(
                viewModel.checked_icon_fragment_result,
                viewModel.checked_color_fragment_result
            )
            navigator.back()
        }
        changeMenuButtonClickListener { apply() }

        val button = CoreButton(context)
        button.gravity = Gravity.CENTER
        button.setText(R.string.apply)
        button.setOnClickListener { apply() }
        button.layoutParams(frameLayoutParams().matchWidth().wrapHeight().gravity(Gravity.BOTTOM))
        addFloatingView(button)

        return contentView
    }

    private companion object {
        const val checked_color_arg = "ExerciseIconSettingsFragment_checked_color_arg"
        const val checked_icon_arg = "ExerciseIconSettingsFragment_checked_icon_arg"
    }

}