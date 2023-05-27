package ru.freeit.crazytraining.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.BaseFragment
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout

class SettingsFragment : BaseFragment() {

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)

        changeTitle(getString(R.string.settings))

        return contentView
    }

}