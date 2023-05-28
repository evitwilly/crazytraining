package ru.freeit.crazytraining.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import ru.freeit.crazytraining.R
import ru.freeit.crazytraining.core.App
import ru.freeit.crazytraining.core.navigation.BaseFragment
import ru.freeit.crazytraining.core.theming.extensions.dp
import ru.freeit.crazytraining.core.theming.extensions.layoutParams
import ru.freeit.crazytraining.core.theming.extensions.linearLayoutParams
import ru.freeit.crazytraining.core.theming.extensions.padding
import ru.freeit.crazytraining.core.theming.layout.components.CoreLinearLayout
import ru.freeit.crazytraining.core.theming.view.CaptionTextView
import ru.freeit.crazytraining.settings.view.ThemeSwitchView

class SettingsFragment : BaseFragment() {

    override fun createView(context: Context, bundle: Bundle?): View {
        val contentView = CoreLinearLayout(context)
        contentView.padding(context.dp(16))
        contentView.orientation = LinearLayout.VERTICAL

        changeTitle(getString(R.string.settings))

        val themeCaptionView = CaptionTextView(context)
        themeCaptionView.setText(R.string.select_theme)
        themeCaptionView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        contentView.addView(themeCaptionView)

        val themeManager = (context.applicationContext as App).themeManager
        val themeSwitchView = ThemeSwitchView(context)
        themeSwitchView.changeThemeSelectListener { theme -> themeManager.changeTheme(theme) }
        themeSwitchView.layoutParams(linearLayoutParams().width(context.dp(120)).height(context.dp(56)).marginTop(context.dp(8)))
        contentView.addView(themeSwitchView)

        val daysCaptionView = CaptionTextView(context)
        daysCaptionView.setText(R.string.select_training_days)
        daysCaptionView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(16)))
        contentView.addView(daysCaptionView)

        return contentView
    }

}