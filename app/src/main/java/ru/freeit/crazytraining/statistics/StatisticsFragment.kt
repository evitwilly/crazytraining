package ru.freeit.crazytraining.statistics

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.ResourcesProviderImpl
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.padding
import ru.freeit.crazytraining.core.navigation.fragment.BaseFragment
import ru.freeit.crazytraining.core.theming.adapter.CoreAdapter
import ru.freeit.crazytraining.core.theming.adapter.VerticalSpaceItemDecoration
import ru.freeit.crazytraining.exercise.data.database.ExerciseDatabase
import ru.freeit.crazytraining.exercise.data.database.ExerciseSetDatabase
import ru.freeit.crazytraining.statistics.adapter.StatisticsViewHolder
import ru.freeit.crazytraining.statistics.repository.StatisticsRepositoryImpl

class StatisticsFragment : BaseFragment<StatisticsViewModel>() {

    override val viewModelKClass: Class<StatisticsViewModel> = StatisticsViewModel::class.java
    override fun viewModelConstructor(ctx: Context, bundle: Bundle?): StatisticsViewModel {
        val coreSQLiteOpenHelper = (ctx.applicationContext as App).coreSQLiteOpenHelper
        return StatisticsViewModel(
            StatisticsRepositoryImpl(
                ExerciseDatabase(coreSQLiteOpenHelper),
                ExerciseSetDatabase(coreSQLiteOpenHelper),
                ResourcesProviderImpl(resources)
            )
        )
    }

    override fun createView(context: Context, bundle: Bundle?): View {
        changeTitle(getString(R.string.statistics))

        val listView = RecyclerView(context)
        listView.clipToPadding = false
        listView.addItemDecoration(VerticalSpaceItemDecoration(context.dp(16)))
        listView.padding(context.dp(16))
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.state.observe(viewLifecycleOwner) { items ->
            listView.adapter = CoreAdapter(items, StatisticsViewHolder::from)
        }

        return listView
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

}