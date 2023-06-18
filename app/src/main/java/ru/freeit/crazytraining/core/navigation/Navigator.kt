package ru.freeit.crazytraining.core.navigation

import androidx.fragment.app.FragmentManager
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.dialogs.CoreDialog
import ru.freeit.crazytraining.core.navigation.fragment.BaseFragment

class Navigator(private val fragmentManager: FragmentManager) {

    val is_not_top_fragment: Boolean
        get() = fragmentManager.backStackEntryCount > 0

    fun replace(fragment: BaseFragment<*>) {
        if (fragmentManager.fragments.lastOrNull()?.javaClass != fragment.javaClass) {
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    fun push(fragment: BaseFragment<*>) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment::class.simpleName)
            .commit()
    }

    fun show(dialog: CoreDialog) {
        dialog.show(fragmentManager, dialog.name)
    }

    fun back() {
        fragmentManager.popBackStack()
    }

}