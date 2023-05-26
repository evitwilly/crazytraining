package ru.freeit.crazytraining.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.navigation.Navigator
import ru.freeit.crazytraining.training.TrainingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentContainer = FrameLayout(this)
        fragmentContainer.id = R.id.fragment_container
        setContentView(fragmentContainer)

        val navigator = Navigator(supportFragmentManager)
        navigator.init(TrainingFragment(), savedInstanceState)

    }

}