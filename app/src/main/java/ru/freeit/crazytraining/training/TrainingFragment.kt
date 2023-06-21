package ru.freeit.crazytraining.training

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.navigation.dialogs.ButtonsAlertDialog
import ru.freeit.crazytraining.core.navigation.dialogs.ButtonsAlertDialogResult
import ru.freeit.crazytraining.core.navigation.fragment.BaseFragment
import ru.freeit.crazytraining.core.repository.CalendarRepositoryImpl
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepositoryImpl
import ru.freeit.crazytraining.settings.SettingsFragment
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.training.adapter.TrainingListAdapter
import ru.freeit.crazytraining.training.dialogs.MeasuredValuesDialog
import ru.freeit.crazytraining.training.dialogs.MeasuredValuesDialogResult
import ru.freeit.crazytraining.training.view.TrainingDateTextView

class TrainingFragment : BaseFragment<TrainingViewModel>() {

    override val viewModelKClass: Class<TrainingViewModel> = TrainingViewModel::class.java
    override fun viewModelConstructor(ctx: Context): TrainingViewModel {
        val app = ctx.applicationContext as App
        val sqliteOpenHelper = app.coreSQLiteOpenHelper
        return TrainingViewModel(
            exerciseListRepository = ExerciseListRepositoryImpl(
                ExerciseDatabase(sqliteOpenHelper),
                ExerciseSetDatabase(sqliteOpenHelper)
            ),
            calendarRepository = CalendarRepositoryImpl(),
            checkedWeekdaysRepository = CheckedWeekdaysRepository.Base(app.persistenceSimpleDataStorage)
        )
    }

    private val adapter = TrainingListAdapter(
        addSetListener = { model ->
            viewModel.cacheExercise(model)
            navigator.show(MeasuredValuesDialog(model.measuredValueModel))
        },
        removeSetListener = { model ->
            viewModel.cacheExerciseSet(model)
            navigator.show(ButtonsAlertDialog(
                title = "",
                message = getString(R.string.do_you_really_want_to_remove_item),
                buttons = ButtonsAlertDialog.Buttons.OK_CANCEL
            ))
        }
    )

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL

        changeMenuButtonVisible(true)
        changeMenuButtonDrawableResource(R.drawable.ic_settings)
        changeMenuButtonClickListener { navigator.push(SettingsFragment()) }

        val dateView = TrainingDateTextView(context)
        dateView.layoutParams(linearLayoutParams().wrap().marginTop(context.dp(8))
            .marginStart(context.dp(16))
            .marginEnd(context.dp(16)))
        contentView.addView(dateView)

        val listView = RecyclerView(context)
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listView.clipToPadding = false
        listView.padding(top = context.dp(8), start = context.dp(16), end = context.dp(16), bottom = context.dp(64))
        listView.layoutParams(linearLayoutParams().matchWidth().height(0).weight(1f))
        listView.adapter = adapter
        contentView.addView(listView)

        viewModel.textState.observe(viewLifecycleOwner) { state ->
            changeTitle(state.title(context))
            state.date(dateView)
        }

        viewModel.trainingState.observe(viewLifecycleOwner) { state -> adapter.submitList(state.items) }

        val fragmentAddSetResult = MeasuredValuesDialogResult(parentFragmentManager)
        fragmentAddSetResult.onResult(viewLifecycleOwner) { amount ->
            viewModel.addSet(amount)
        }

        val fragmentRemoveSetResult = ButtonsAlertDialogResult(parentFragmentManager)
        fragmentRemoveSetResult.onOkClick(viewLifecycleOwner) {
            viewModel.removeSet()
        }

        return contentView
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateState()
    }

}