package ru.freeit.crazytraining.core

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.Navigator
import ru.freeit.crazytraining.core.extensions.dp
import ru.freeit.crazytraining.core.extensions.frameLayoutParams
import ru.freeit.crazytraining.core.extensions.layoutParams
import ru.freeit.crazytraining.core.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.layout.components.BottomNavigationView
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout
import ru.freeit.crazytraining.statistics.StatisticsFragment
import ru.freeit.crazytraining.exercise.list.ExerciseListFragment
import ru.freeit.crazytraining.training.TrainingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val coreFrameView = CoreFrameLayout(this)
        setContentView(coreFrameView)

        val bottomNavigationViewHeight = dp(56)

        val fragmentContainer = CoreFrameLayout(this)
        fragmentContainer.id = R.id.fragment_container
        fragmentContainer.layoutParams(linearLayoutParams().match().marginBottom(bottomNavigationViewHeight))
        coreFrameView.addView(fragmentContainer)

        val navigator = Navigator(supportFragmentManager)

        val bottomNavigationView = BottomNavigationView(this)
        bottomNavigationView.layoutParams(frameLayoutParams().matchWidth().height(bottomNavigationViewHeight).gravity(Gravity.BOTTOM))
        bottomNavigationView.changeTabs(listOf(
            BottomNavigationView.BottomNavigationTab(
                drawableResource = R.drawable.ic_training,
                stringResource = R.string.training,
                clickListener = {
                    navigator.replace(TrainingFragment())
                }
            ),
            BottomNavigationView.BottomNavigationTab(
                drawableResource = R.drawable.ic_exercises,
                stringResource = R.string.exercises,
                clickListener = { navigator.replace(ExerciseListFragment()) }
            ),
            BottomNavigationView.BottomNavigationTab(
                drawableResource = R.drawable.ic_history,
                stringResource = R.string.statistics,
                clickListener = { navigator.replace(StatisticsFragment()) }
            )
        ))
        coreFrameView.addView(bottomNavigationView)

        if (savedInstanceState == null) {
            bottomNavigationView.changeSelectedTab(0)
        }

    }

}