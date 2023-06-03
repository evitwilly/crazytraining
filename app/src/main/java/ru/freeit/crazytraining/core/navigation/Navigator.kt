package ru.freeit.crazytraining.core.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import ru.freeit.crazytraining.R

class Navigator(private val fragmentManager: FragmentManager) {

    val is_not_top_fragment: Boolean
        get() = fragmentManager.backStackEntryCount > 0

    fun init(fragment: BaseFragment<*>, bundle: Bundle?) {
        if (bundle == null) {
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

    fun back() {
        fragmentManager.popBackStack()
    }

}