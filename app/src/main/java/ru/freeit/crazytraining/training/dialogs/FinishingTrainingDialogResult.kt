package ru.freeit.crazytraining.training.dialogs

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class FinishingTrainingDialogResult(private val fragmentManager: FragmentManager) {

    fun onSuccessResult(lifecycleOwner: LifecycleOwner, listener: (String, String, Int) -> Unit) {
        fragmentManager.setFragmentResultListener(success_request_key, lifecycleOwner) { _, data ->
            val trainingType = data.getString(data_key_training_type).orEmpty()
            val comment = data.getString(data_key_comment).orEmpty()
            val rating = data.getInt(data_key_rating, 4)
            listener.invoke(trainingType, comment, rating)
        }
    }

    fun successResult(trainingType: String, comment: String, rating: Int) {
        fragmentManager.setFragmentResult(success_request_key, bundleOf(
            data_key_training_type to trainingType,
            data_key_comment to comment,
            data_key_rating to rating
        ))
    }

    private companion object {
        const val success_request_key = "FinishingTrainingDialog_success_request_key"
        const val data_key_training_type = "FinishingTrainingDialog_data_key_training_type"
        const val data_key_comment = "FinishingTrainingDialog_data_key_comment"
        const val data_key_rating = "FinishingTrainingDialog_data_key_rating"
    }

}