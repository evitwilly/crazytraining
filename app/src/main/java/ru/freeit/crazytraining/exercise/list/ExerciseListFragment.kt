package ru.freeit.crazytraining.exercise.list

import android.content.Context
import android.os.Bundle
import android.view.View
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.BaseFragment
import ru.freeit.crazytraining.core.navigation.BaseViewModel
import ru.freeit.crazytraining.core.theming.layout.components.CoreFrameLayout

class ExerciseListFragment : BaseFragment<BaseViewModel>() {

    override fun createView(context: Context, bundle: Bundle?): View {
        changeTitle(getString(R.string.exercises))
        return CoreFrameLayout(context)
    }

}