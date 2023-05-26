package ru.freeit.crazytraining.training

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import ru.freeit.crazytraining.core.navigation.BaseFragment
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout

class TrainingFragment : BaseFragment() {

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL
        changeTitle("Training")

        return contentView
    }

}