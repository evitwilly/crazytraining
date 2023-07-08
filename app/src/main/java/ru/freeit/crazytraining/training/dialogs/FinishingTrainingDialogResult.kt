package ru.freeit.crazytraining.training.dialogs

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class FinishingTrainingDialogResult(private val fragmentManager: FragmentManager) {

    fun onResult(lifecycleOwner: LifecycleOwner, listener: (String, Int) -> Unit) {
        fragmentManager.setFragmentResultListener(request_key, lifecycleOwner) { _, data ->
            val comment = data.getString(data_key_comment).orEmpty()
            val rating = data.getInt(data_key_rating, 4)
            listener.invoke(comment, rating)
        }
    }

    fun result(comment: String, rating: Int) {
        fragmentManager.setFragmentResult(request_key, bundleOf(
            data_key_comment to comment,
            data_key_rating to rating
        ))
    }

    private companion object {
        const val request_key = "FinishingTrainingDialog_request_key"
        const val data_key_comment = "FinishingTrainingDialog_data_key_comment"
        const val data_key_rating = "FinishingTrainingDialog_data_key_rating"
    }

}