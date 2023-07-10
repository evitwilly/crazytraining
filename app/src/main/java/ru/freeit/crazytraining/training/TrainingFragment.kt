package ru.freeit.crazytraining.training

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.navigation.dialogs.ButtonsAlertDialog
import ru.freeit.crazytraining.core.navigation.dialogs.ButtonsAlertDialogResult
import ru.freeit.crazytraining.core.navigation.fragment.BaseFragment
import ru.freeit.crazytraining.core.repository.CalendarRepositoryImpl
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.viewmodel.SavedInstanceStateImpl
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.data.repository.ExerciseActiveRepositoryImpl
import ru.freeit.crazytraining.settings.SettingsFragment
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepositoryImpl
import ru.freeit.crazytraining.training.adapter.TrainingListAdapter
import ru.freeit.crazytraining.training.data.database.TrainingDatabase
import ru.freeit.crazytraining.training.data.repository.ExerciseSetsRepositoryImpl
import ru.freeit.crazytraining.training.data.repository.TrainingRepositoryImpl
import ru.freeit.crazytraining.training.dialogs.ExerciseAddSetDialog
import ru.freeit.crazytraining.training.dialogs.ExerciseAddSetDialogResult
import ru.freeit.crazytraining.training.dialogs.FinishingTrainingDialog
import ru.freeit.crazytraining.training.dialogs.FinishingTrainingDialogResult
import ru.freeit.crazytraining.training.view.TrainingDateView
import ru.freeit.crazytraining.training.view.TrainingStatusView
import ru.freeit.crazytraining.training.viewmodel_states.TrainingActiveState
import ru.freeit.crazytraining.training.viewmodel_states.TrainingDetailStateListeners

class TrainingFragment : BaseFragment<TrainingViewModel>() {

    override val viewModelKClass: Class<TrainingViewModel> = TrainingViewModel::class.java
    override fun viewModelConstructor(ctx: Context, bundle: Bundle?): TrainingViewModel {
        val app = ctx.applicationContext as App
        val sqliteOpenHelper = app.coreSQLiteOpenHelper
        val exerciseSetDatabase = ExerciseSetDatabase(sqliteOpenHelper)
        return TrainingViewModel(
            savedState = SavedInstanceStateImpl(bundle),
            exerciseSetsRepository = ExerciseSetsRepositoryImpl(exerciseSetDatabase),
            calendarRepository = CalendarRepositoryImpl(),
            checkedWeekdaysRepository = CheckedWeekdaysRepositoryImpl(app.persistenceSimpleDataStorage),
            trainingRepository = TrainingRepositoryImpl(
                ExerciseDatabase(sqliteOpenHelper),
                ExerciseSetDatabase(sqliteOpenHelper),
                TrainingDatabase(sqliteOpenHelper),
                ExerciseActiveRepositoryImpl(app.persistenceSimpleDataStorage)
            )
        )
    }

    private val adapter = TrainingListAdapter(
        TrainingDetailStateListeners(
            addListener = { model ->
                viewModel.cacheExercise(model)
                navigator.show(ExerciseAddSetDialog(model.unit))
            },
            removeListener = { model ->
                viewModel.cacheExerciseSet(model)
                navigator.show(ButtonsAlertDialog(
                    title = "",
                    message = getString(R.string.do_you_really_want_to_remove_item),
                    buttons = ButtonsAlertDialog.Buttons.OK_CANCEL
                ))
            },
            plusListener = { viewModel.plusSimilarSet(it) },
            minusListener = { viewModel.minusSimilarSet(it) }
        )
    )

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL

        changeMenuButtonVisible(true)
        changeMenuButtonDrawableResource(R.drawable.ic_settings)
        changeMenuButtonClickListener { navigator.push(SettingsFragment()) }

        val dateView = TrainingDateView(context)
        dateView.layoutParams(linearLayoutParams().wrap().marginTop(context.dp(8))
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16)))
        contentView.addView(dateView)

        val listView = RecyclerView(context)
        listView.itemAnimator = null
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listView.clipToPadding = false
        listView.isMotionEventSplittingEnabled = false
        listView.padding(top = context.dp(8), start = context.dp(16), end = context.dp(16), bottom = context.dp(64))
        listView.layoutParams(linearLayoutParams().matchWidth().height(0).weight(1f))
        listView.adapter = adapter
        contentView.addView(listView)

        val statusView = TrainingStatusView(context)
        statusView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        contentView.addView(statusView)

        val buttonView = CoreButton(context)
        buttonView.gravity = Gravity.CENTER
        buttonView.isVisible = false
        buttonView.layoutParams(frameLayoutParams().matchWidth().wrapHeight().gravity(Gravity.BOTTOM))
        buttonView.setOnClickListener { viewModel.buttonClick() }
        addFloatingView(buttonView)

        viewModel.textState.observe(viewLifecycleOwner) { state ->
            changeTitle(state.title(context))
            state.date(dateView)
        }

        viewModel.listState.observe(viewLifecycleOwner) { state -> adapter.submitList(state.items) }

        viewModel.isVisibleFinishingTrainingDialog.observe(viewLifecycleOwner) { type ->
            navigator.show(FinishingTrainingDialog(type))
        }

        viewModel.activeState.observe(viewLifecycleOwner) { state ->
            listView.isVisible = false
            statusView.isVisible = false
            buttonView.isVisible = false

            when (state) {
                is TrainingActiveState.Training -> {
                    listView.isVisible = true

                    buttonView.isVisible = true
                    buttonView.setText(state.buttonTitle)
                }
                is TrainingActiveState.Weekend -> {
                    statusView.changeTitle(getString(R.string.weekend_title))
                    statusView.changeIcon(R.drawable.ic_weekend)

                    statusView.isVisible = true
                }
                is TrainingActiveState.Finished -> {
                    statusView.changeTitle(getString(R.string.finished_title))
                    statusView.changeIcon(R.drawable.ic_finished)

                    statusView.isVisible = true
                    buttonView.isVisible = true
                    buttonView.setText(state.buttonTitle)
                }
            }
        }

        val fragmentAddSetResult = ExerciseAddSetDialogResult(parentFragmentManager)
        fragmentAddSetResult.onResult(viewLifecycleOwner) { amount ->
            viewModel.addSet(amount)
        }

        val fragmentRemoveSetResult = ButtonsAlertDialogResult(parentFragmentManager)
        fragmentRemoveSetResult.onOkClick(viewLifecycleOwner) {
            viewModel.removeSet()
        }

        val fragmentFinishingTrainingResult = FinishingTrainingDialogResult(parentFragmentManager)
        fragmentFinishingTrainingResult.onSuccessResult(viewLifecycleOwner) { type, comment, rating ->
            viewModel.finishTraining(type, comment, rating)
        }

        return contentView
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateState()
    }

}