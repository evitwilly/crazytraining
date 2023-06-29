package ru.freeit.crazytraining.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.navigation.dialogs.ButtonsAlertDialog
import ru.freeit.crazytraining.core.navigation.dialogs.ButtonsAlertDialogResult
import ru.freeit.crazytraining.core.navigation.fragment.BaseFragment
import ru.freeit.crazytraining.core.repository.CalendarRepositoryImpl
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.view.CoreTextView
import ru.freeit.crazytraining.core.theming.view.FlowLayout
import ru.freeit.crazytraining.core.viewmodel.SavedInstanceStateImpl
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepositoryImpl
import ru.freeit.crazytraining.settings.view.ThemeSwitchView
import ru.freeit.crazytraining.training.repository.ExerciseSetsRepositoryImpl

class SettingsFragment : BaseFragment<SettingsViewModel>() {

    override val viewModelKClass: Class<SettingsViewModel> = SettingsViewModel::class.java
    override fun viewModelConstructor(ctx: Context, bundle: Bundle?): SettingsViewModel {
        val app = ctx.applicationContext as App
        val simpleDataStorage = app.persistenceSimpleDataStorage
        val coreSQLiteOpenHelper = app.coreSQLiteOpenHelper
        return SettingsViewModel(
            savedState = SavedInstanceStateImpl(bundle),
            weekdaysRepository = CheckedWeekdaysRepositoryImpl(simpleDataStorage),
            calendarRepository = CalendarRepositoryImpl(),
            exerciseSetsRepository = ExerciseSetsRepositoryImpl(ExerciseSetDatabase(coreSQLiteOpenHelper))
        )
    }

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.padding(context.dp(16))
        contentView.orientation = LinearLayout.VERTICAL

        changeTitle(getString(R.string.settings))

        val themeCaptionView = CoreTextView(context)
        themeCaptionView.setText(R.string.select_theme)
        themeCaptionView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        contentView.addView(themeCaptionView)

        val themeManager = (context.applicationContext as App).themeManager
        val themeSwitchView = ThemeSwitchView(context)
        themeSwitchView.changeThemeSelectListener { theme -> themeManager.changeTheme(theme) }
        themeSwitchView.layoutParams(linearLayoutParams().width(context.dp(120)).height(context.dp(56)).marginTop(context.dp(8)))
        contentView.addView(themeSwitchView)

        val daysCaptionView = CoreTextView(context)
        daysCaptionView.setText(R.string.select_training_days)
        daysCaptionView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(16)))
        contentView.addView(daysCaptionView)

        val weekdaysLayoutView = FlowLayout(context)
        weekdaysLayoutView.changeHorizontalSpacing(context.dp(8))
        weekdaysLayoutView.changeVerticalSpacing(context.dp(8f))
        weekdaysLayoutView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(8)))
        contentView.addView(weekdaysLayoutView)

        val fragmentResult = ButtonsAlertDialogResult(parentFragmentManager)
        fragmentResult.onOkClick(viewLifecycleOwner) {
            viewModel.dialogOkClick()
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.bindView(weekdaysLayoutView, viewModel::changeWeekdayState)
        }

        viewModel.acceptDialogState.observe(viewLifecycleOwner) { _ ->
            navigator.show(ButtonsAlertDialog(
                title = "",
                message = getString(R.string.training_cancel_title),
                buttons = ButtonsAlertDialog.Buttons.OK_CANCEL
            ))
        }

        return contentView
    }

}