package ru.freeit.crazytraining.exercise.detail

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class ExerciseIconSettingsFragmentResult(private val fragment: FragmentManager) {

    fun onResult(lifecycleOwner: LifecycleOwner, listener: (checkedColor: Int, checkedIcon: Int) -> Unit) {
        fragment.setFragmentResultListener(request_key, lifecycleOwner) { _, bundle ->
            listener.invoke(bundle.getInt(icon_key), bundle.getInt(color_key))
        }
    }

    fun result(checkedColor: Int, checkedIcon: Int) {
        fragment.setFragmentResult(request_key, bundleOf(
            icon_key to checkedIcon,
            color_key to checkedColor
        ))
    }

    private companion object {
        const val request_key = "ExerciseIconSettingsFragmentResult_request_key"
        const val icon_key = "ExerciseIconSettingsFragmentResult_icon_key"
        const val color_key = "ExerciseIconSettingsFragmentResult_color_key"
    }

}