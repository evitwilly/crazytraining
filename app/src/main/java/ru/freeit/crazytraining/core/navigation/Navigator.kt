package ru.freeit.crazytraining.core.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.dialogs.CoreDialog
import ru.freeit.crazytraining.core.navigation.fragment.BaseFragment

class Navigator(private val fragmentManager: FragmentManager) {

    val is_not_top_fragment: Boolean
        get() = fragmentManager.backStackEntryCount > 0

    fun replace(fragment: BaseFragment<*>) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment::class.simpleName)
            .commit()
    }

    fun push(fragment: BaseFragment<*>) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment::class.simpleName)
            .commit()
    }

    fun show(dialog: CoreDialog) {
        if (fragmentManager.isExistsFragment(dialog)) return
        dialog.show(fragmentManager, dialog.name)
    }

    private fun FragmentManager.isExistsFragment(fragment: Fragment) =
        fragments.find { it.javaClass == fragment.javaClass } != null

    fun back() {
        fragmentManager.popBackStack()
    }

}