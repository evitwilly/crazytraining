package ru.freeit.crazytraining.training

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.navigation.BaseFragment
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.exercise.ExerciseFragment
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepositoryImpl
import ru.freeit.crazytraining.settings.SettingsFragment
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository
import ru.freeit.crazytraining.training.view.TrainingDateTextView

class TrainingFragment : BaseFragment<TrainingViewModel>() {

    override val viewModelKClass: Class<TrainingViewModel> = TrainingViewModel::class.java
    override fun viewModelConstructor(ctx: Context): TrainingViewModel {
        val app = ctx.applicationContext as App
        return TrainingViewModel(
            exerciseListRepository = ExerciseListRepositoryImpl(ExerciseDatabase(app.coreSQLiteOpenHelper)),
            calendarRepository = CalendarRepository.Base(),
            checkedWeekdaysRepository = CheckedWeekdaysRepository.Base(app.persistenceSimpleDataStorage)
        )
    }

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL
        contentView.padding(horizontal = context.dp(16), vertical = context.dp(8))

        changeMenuButtonVisible(true)
        changeMenuButtonDrawableResource(R.drawable.ic_settings)
        changeMenuButtonClickListener { navigator.push(SettingsFragment()) }

        val dateView = TrainingDateTextView(context)
        dateView.layoutParams(linearLayoutParams().wrap())
        contentView.addView(dateView)

        val listView = RecyclerView(context)
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listView.layoutParams(linearLayoutParams().matchWidth().height(0).weight(1f).marginTop(context.dp(8)))
        contentView.addView(listView)

        val trainingAddButton = CoreButton(context)
        trainingAddButton.setText(R.string.add_exercise)
        trainingAddButton.changeStartIcon(R.drawable.ic_add, 24)
        trainingAddButton.setOnClickListener { navigator.push(ExerciseFragment()) }
        trainingAddButton.layoutParams(frameLayoutParams().wrap()
            .gravity(Gravity.BOTTOM or Gravity.END)
            .marginEnd(context.dp(16))
            .marginBottom(context.dp(16)))
        addFloatingView(trainingAddButton)

        viewModel.titleState.observe(viewLifecycleOwner) { title ->
            changeTitle(getString(title))
        }

        viewModel.dateState.observe(viewLifecycleOwner) { date ->
            dateView.text = date.replaceFirstChar { it.titlecase() }
        }

        viewModel.exerciseListState.observe(viewLifecycleOwner) { listState ->
            listView.adapter = listState.adapter
        }

        return contentView
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateState()
    }

}