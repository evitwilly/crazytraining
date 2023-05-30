package ru.freeit.crazytraining.training

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.navigation.BaseFragment
import ru.freeit.crazytraining.core.repository.CalendarRepository
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.viewmodel.viewModelFactory
import ru.freeit.crazytraining.settings.SettingsFragment
import ru.freeit.crazytraining.settings.repository.CheckedWeekdaysRepository

class TrainingFragment : BaseFragment() {

    private var viewModel: TrainingViewModel? = null
    
    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL

        changeTitle(getString(R.string.training))
        changeMenuButtonVisible(true)
        changeMenuButtonDrawableResource(R.drawable.ic_settings)
        changeMenuButtonClickListener { navigator.push(SettingsFragment()) }

        val simpleDataStorage = (context.applicationContext as App).persistenceSimpleDataStorage
        val factory = viewModelFactory { TrainingViewModel(
            calendarRepository = CalendarRepository.Base(),
            checkedWeekdaysRepository = CheckedWeekdaysRepository.Base(simpleDataStorage)
        ) }
        val viewModel = ViewModelProvider(this, factory)[TrainingViewModel::class.java]
        this.viewModel = viewModel
        viewModel.titleState.observe(viewLifecycleOwner) { title ->
            changeTitle(getString(title))
        }

        return contentView
    }

    override fun onStart() {
        super.onStart()
        viewModel?.checkToday()
    }

}