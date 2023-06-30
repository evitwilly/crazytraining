package ru.freeit.crazytraining.training.dialogs

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class ExerciseAddSetDialogResult(private val fragmentManager: FragmentManager) {

    fun onResult(lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
        fragmentManager.setFragmentResultListener(request_key, lifecycleOwner) { _, data ->
            listener.invoke(data.getInt(data_key))
        }
    }

    fun result(amount: Int) {
        fragmentManager.setFragmentResult(request_key, bundleOf(data_key to amount))
    }

    private companion object {
        const val request_key = "ExerciseAddSetDialogResult_request_key"
        const val data_key = "ExerciseAddSetDialogResult_data_key"
    }

}