package ru.freeit.crazytraining.exercise.list

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.navigation.BaseFragment
import ru.freeit.crazytraining.core.theming.extensions.*
import ru.freeit.crazytraining.core.theming.view.CoreButton
import ru.freeit.crazytraining.exercise.ExerciseFragment
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.exercise.data.repository.ExerciseListRepositoryImpl

class ExerciseListFragment : BaseFragment<ExerciseListViewModel>() {

    override val viewModelKClass = ExerciseListViewModel::class.java
    override fun viewModelConstructor(ctx: Context): ExerciseListViewModel {
        val coreSQLiteOpenHelper = (ctx.applicationContext as App).coreSQLiteOpenHelper
        return ExerciseListViewModel(ExerciseListRepositoryImpl(
            ExerciseDatabase(coreSQLiteOpenHelper),
            ExerciseSetDatabase(coreSQLiteOpenHelper)
        ))
    }

    override fun createView(context: Context, bundle: Bundle?): View {
        changeTitle(getString(R.string.exercises))

        val listView = RecyclerView(context)
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listView.clipToPadding = false
        listView.padding(top = context.dp(8), start = context.dp(16), end = context.dp(16), bottom = context.dp(64))
        listView.layoutParams(linearLayoutParams().matchWidth().height(0).weight(1f))

        val trainingAddButton = CoreButton(context)
        trainingAddButton.setText(R.string.add_exercise)
        trainingAddButton.changeStartIcon(R.drawable.ic_add, 24)
        trainingAddButton.setOnClickListener { navigator.push(ExerciseFragment()) }
        trainingAddButton.padding(context.dp(8))
        trainingAddButton.layoutParams(frameLayoutParams().wrap()
            .gravity(Gravity.BOTTOM or Gravity.END)
            .marginEnd(context.dp(16))
            .marginBottom(context.dp(16)))
        addFloatingView(trainingAddButton)

        viewModel.exerciseListState.observe(viewLifecycleOwner) { listState ->
            listView.adapter = listState.adapter
        }

        return listView
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateState()
    }

}