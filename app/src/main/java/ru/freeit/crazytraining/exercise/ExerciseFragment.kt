package ru.freeit.crazytraining.exercise

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.doAfterTextChanged
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.BaseFragment
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.typeface.TypefaceStyle
import ru.freeit.crazytraining.core.theming.view.CaptionTextView
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.theming.view.CoreEditText
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.repository.ExerciseListRepositoryImpl
import ru.freeit.crazytraining.exercise.repository.ExerciseResourcesRepositoryImpl

class ExerciseFragment : BaseFragment<ExerciseViewModel>() {

    override val viewModelKClass: Class<ExerciseViewModel> = ExerciseViewModel::class.java
    override fun viewModelConstructor(ctx: Context): ExerciseViewModel {
        return ExerciseViewModel(
            listRepository = ExerciseListRepositoryImpl(),
            resourcesRepository = ExerciseResourcesRepositoryImpl()
        )
    }

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL
        contentView.padding(top = context.dp(8), bottom = context.dp(48))

        changeMenuButtonDrawableResource(R.drawable.ic_check)
        changeMenuButtonVisible(true)
        changeTitle(getString(R.string.add_exercise))

        val exerciseFrameView = CoreFrameLayout(context)
        exerciseFrameView.padding(start = context.dp(16), end = context.dp(16))
        exerciseFrameView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(16)))
        contentView.addView(exerciseFrameView)

        val selectedIconView = AppCompatImageView(context)
        selectedIconView.padding(context.dp(12))
        selectedIconView.layoutParams(frameLayoutParams().width(context.dp(64)).height(context.dp(64)))
        exerciseFrameView.addView(selectedIconView)

        val selectedTitleView = CoreTextView(context)
        selectedTitleView.fontSize(18f)
        selectedTitleView.fontFamily(TypefaceStyle.MEDIUM)
        selectedTitleView.layoutParams(frameLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(72)))
        exerciseFrameView.addView(selectedTitleView)

        val titleCaptionView = CaptionTextView(context)
        titleCaptionView.text = getString(R.string.write_exercise_name)
        titleCaptionView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(16)))
        contentView.addView(titleCaptionView)

        val titleEditView = CoreEditText(context)
        titleEditView.isSingleLine = true
        titleEditView.setHint(R.string.exercise_name)
        titleEditView.fontSize(18f)
        titleEditView.fontFamily(TypefaceStyle.MEDIUM)
        titleEditView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(8)))
        contentView.addView(titleEditView)

        titleEditView.doAfterTextChanged {
            selectedTitleView.text = it.toString()
        }

        val iconsCaptionView = CaptionTextView(context)
        iconsCaptionView.text = getString(R.string.choose_icon)
        iconsCaptionView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(16)))
        contentView.addView(iconsCaptionView)

        val iconsListView = CoreLinearLayout(context)
        iconsListView.orientation = LinearLayout.HORIZONTAL
        val iconsScrollingView = HorizontalScrollView(context)
        iconsScrollingView.clipToPadding = false
        iconsScrollingView.isHorizontalScrollBarEnabled = false
        iconsScrollingView.padding(start = context.dp(16), end = context.dp(16))
        iconsScrollingView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(8)))
        iconsScrollingView.addView(iconsListView)
        contentView.addView(iconsScrollingView)

        val colorsCaptionView = CaptionTextView(context)
        colorsCaptionView.text = getString(R.string.choose_color_for_icon)
        colorsCaptionView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(16)))
        contentView.addView(colorsCaptionView)

        val colorsListView = CoreLinearLayout(context)
        colorsListView.orientation = LinearLayout.HORIZONTAL
        val colorsScrollingView = HorizontalScrollView(context)
        colorsScrollingView.clipToPadding = false
        colorsScrollingView.isHorizontalScrollBarEnabled = false
        colorsScrollingView.padding(start = context.dp(16), end = context.dp(16))
        colorsScrollingView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(8)))
        colorsScrollingView.addView(colorsListView)
        contentView.addView(colorsScrollingView)

        val measuredValuesCaptionView = CaptionTextView(context)
        measuredValuesCaptionView.text = getString(R.string.choose_measured_value_for_exercise)
        measuredValuesCaptionView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(16)))
        contentView.addView(measuredValuesCaptionView)

        val measuredValuesListView = CoreLinearLayout(context)
        measuredValuesListView.orientation = LinearLayout.VERTICAL
        measuredValuesListView.layoutParams(linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(8)))
        contentView.addView(measuredValuesListView)

        val button = CoreButton(context)
        button.gravity = Gravity.CENTER
        button.setText(R.string.add_exercise)
        button.layoutParams(frameLayoutParams().matchWidth().wrapHeight().gravity(Gravity.BOTTOM))
        addFloatingView(button)

        viewModel.addingExerciseState.observe(viewLifecycleOwner) { state ->
            state.bindViews(selectedTitleView, selectedIconView)
            state.measuredState.bindView(measuredValuesListView, viewModel::checkMeasuredState)
        }
        viewModel.settingsIconState.observe(viewLifecycleOwner) { state ->
            state.bindIconsView(iconsListView, viewModel::checkIcon)
            state.bindColorsView(colorsListView, viewModel::checkColor)
        }
        titleEditView.doAfterTextChanged { title -> viewModel.changeTitle(title.toString()) }

        button.setOnClickListener { viewModel.apply() }
        changeMenuButtonClickListener { viewModel.apply() }

        val scrollView = ScrollView(context)
        scrollView.addView(contentView)
        return scrollView
    }

}