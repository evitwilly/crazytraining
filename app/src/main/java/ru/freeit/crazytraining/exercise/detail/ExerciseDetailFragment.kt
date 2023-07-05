package ru.freeit.crazytraining.exercise.detail

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.navigation.fragment.BaseFragment
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.text.TextAttribute
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.theming.view.CoreEditText
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepositoryImpl
import ru.freeit.crazytraining.exercise.model.ExerciseModel


class ExerciseDetailFragment() : BaseFragment<ExerciseDetailViewModel>() {

    override val viewModelKClass: Class<ExerciseDetailViewModel> = ExerciseDetailViewModel::class.java
    override fun viewModelConstructor(ctx: Context, bundle: Bundle?): ExerciseDetailViewModel {
        val exerciseModel = arguments?.parcelable<ExerciseModel>(exercise_model_arg)
        val coreSQLiteOpenHelper = (ctx.applicationContext as App).coreSQLiteOpenHelper
        return ExerciseDetailViewModel(
            argument = exerciseModel,
            listRepository = ExerciseListRepositoryImpl(
                ExerciseDatabase(coreSQLiteOpenHelper),
                ExerciseSetDatabase(coreSQLiteOpenHelper)
            )
        )
    }

    constructor(model: ExerciseModel) : this() {
        arguments = bundleOf(exercise_model_arg to model)
    }

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL
        contentView.padding(top = context.dp(8), bottom = context.dp(48))

        val argument = arguments?.parcelable<ExerciseModel>(exercise_model_arg)

        changeMenuButtonDrawableResource(R.drawable.ic_check)
        changeMenuButtonVisible(true)
        changeMenuButtonClickListener { viewModel.apply() }
        changeTitle(getString(if (argument == null) R.string.add_exercise else R.string.edit_exercise))

        val titleEditView = CoreEditText(context, TextAttribute.Title2)
        titleEditView.singleLine()
        titleEditView.changeHint(R.string.exercise_name)
        titleEditView.changeMaxLength(40)
        titleEditView.changeText(argument?.title ?: "")
        titleEditView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(8)))
        contentView.addView(titleEditView)

        val unitCaptionView = CoreTextView(context)
        unitCaptionView.text = getString(R.string.choose_measured_value_for_exercise)
        unitCaptionView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(32)))
        contentView.addView(unitCaptionView)

        val unitListView = CoreLinearLayout(context)
        unitListView.orientation = LinearLayout.VERTICAL
        unitListView.layoutParams(
            linearLayoutParams().matchWidth().wrapHeight()
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16))
            .marginTop(context.dp(8)))
        contentView.addView(unitListView)

        val button = CoreButton(context)
        button.gravity = Gravity.CENTER
        button.setText(if (argument == null) R.string.add_exercise else R.string.save)
        button.layoutParams(frameLayoutParams().matchWidth().wrapHeight().gravity(Gravity.BOTTOM))
        button.setOnClickListener { viewModel.apply() }
        addFloatingView(button)

        viewModel.titleError.observe(viewLifecycleOwner) { error ->
            titleEditView.error = CoreEditText.Error.Text(getString(error))
        }

        viewModel.exerciseSettingsState.observe(viewLifecycleOwner) { state ->
            titleEditView.error = CoreEditText.Error.Empty
            with(state) {
                unitListState.bindView(unitListView, viewModel::checkMeasuredState)
                unitCaptionView.isVisible = unitListView.childCount > 1
            }
        }
        titleEditView.changeTextListener { title -> viewModel.changeTitle(title) }

        val scrollView = ScrollView(context)
        scrollView.addView(contentView)
        return scrollView
    }

    private companion object {
        const val exercise_model_arg = "ExerciseDetailFragment_exercise_model_arg"
    }

}