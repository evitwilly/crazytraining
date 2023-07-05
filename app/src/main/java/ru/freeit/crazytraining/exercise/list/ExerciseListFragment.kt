package ru.freeit.crazytraining.exercise.list

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.extensions.*
import ru.freeit.crazytraining.core.navigation.dialogs.ButtonsAlertDialog
import ru.freeit.crazytraining.core.navigation.dialogs.ButtonsAlertDialogResult
import ru.freeit.crazytraining.core.navigation.fragment.BaseFragment
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.core.viewmodel.SavedInstanceStateImpl
import ru.freeit.crazytraining.exercise.detail.ExerciseDetailFragment
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepositoryImpl
import ru.freeit.crazytraining.exercise.list.adapter.ExerciseEditButtonState
import ru.freeit.crazytraining.exercise.list.adapter.ExerciseListAdapter

class ExerciseListFragment : BaseFragment<ExerciseListViewModel>() {

    override val viewModelKClass = ExerciseListViewModel::class.java
    override fun viewModelConstructor(ctx: Context, bundle: Bundle?): ExerciseListViewModel {
        val coreSQLiteOpenHelper = (ctx.applicationContext as App).coreSQLiteOpenHelper
        return ExerciseListViewModel(
            savedState = SavedInstanceStateImpl(bundle),
            repository = ExerciseListRepositoryImpl(
                ExerciseDatabase(coreSQLiteOpenHelper),
                ExerciseSetDatabase(coreSQLiteOpenHelper)
            ),
            itemButtons = listOf(
                ExerciseEditButtonState.Button(
                    stringResource = R.string.edit,
                    clickListener = { model -> navigator.push(ExerciseDetailFragment(model)) }
                ),
                ExerciseEditButtonState.Button(
                    stringResource = R.string.remove,
                    clickListener = { model ->
                        viewModel.cache(model)
                        navigator.show(
                            ButtonsAlertDialog(
                                title = getString(R.string.remove_exercise),
                                message = getString(R.string.remove_exercise_warning),
                                buttons = ButtonsAlertDialog.Buttons.OK_CANCEL
                            )
                        )
                    }
                )
            )
        )
    }

    private val adapter = ExerciseListAdapter()

    override fun createView(context: Context, bundle: Bundle?): View {
        changeTitle(getString(R.string.exercises))

        val listView = RecyclerView(context)
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listView.clipToPadding = false
        listView.isMotionEventSplittingEnabled = false
        listView.padding(top = context.dp(8), start = context.dp(16), end = context.dp(16), bottom = context.dp(64))
        listView.layoutParams(linearLayoutParams().matchWidth().height(0).weight(1f))
        listView.adapter = adapter

        val trainingAddButton = CoreButton(context)
        trainingAddButton.setText(R.string.add_exercise)
        trainingAddButton.changeStartIcon(R.drawable.ic_add, 24)
        trainingAddButton.setOnClickListener { navigator.push(ExerciseDetailFragment()) }
        trainingAddButton.padding(context.dp(8))
        trainingAddButton.layoutParams(
            frameLayoutParams().wrap()
            .gravity(Gravity.BOTTOM or Gravity.END)
            .marginEnd(context.dp(16))
            .marginBottom(context.dp(16)))
        addFloatingView(trainingAddButton)

        val fragmentDialogResult = ButtonsAlertDialogResult(parentFragmentManager)
        fragmentDialogResult.onOkClick(viewLifecycleOwner) {
            viewModel.remove()
        }

        viewModel.exerciseListState.observe(viewLifecycleOwner) { listState ->
            adapter.submitList(listState.items)
        }

        return listView
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateState()
    }

}