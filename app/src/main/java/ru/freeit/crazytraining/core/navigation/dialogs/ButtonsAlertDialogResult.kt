package ru.freeit.crazytraining.core.navigation.dialogs

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class ButtonsAlertDialogResult(private val fragmentManager: FragmentManager) {

    fun okClick() {
        fragmentManager.setFragmentResult(request_key, bundleOf(ok_key to 1))
    }

    fun cancelClick() {
        fragmentManager.setFragmentResult(request_key, bundleOf(cancel_key to 1))
    }

    fun onOkClick(lifecycleOwner: LifecycleOwner, listener: () -> Unit) {
        fragmentManager.setFragmentResultListener(request_key, lifecycleOwner) { _, data ->
            if (data.containsKey(ok_key)) listener.invoke()
        }
    }

    fun onCancelClick(lifecycleOwner: LifecycleOwner, listener: () -> Unit) {
        fragmentManager.setFragmentResultListener(request_key, lifecycleOwner) { _, data ->
            if (data.containsKey(cancel_key)) listener.invoke()
        }
    }

    private companion object {
        const val request_key = "CoreDialogFragmentResult_request_key"
        const val ok_key = "CoreDialogFragmentResult_ok_key"
        const val cancel_key = "CoreDialogFragmentResult_cancel_key"
    }

}