package ru.freeit.crazytraining.core.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.freeit.crazytraining.core.viewmodel.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    private val _navigationBack = SingleLiveEvent<Boolean>()
    val navigationBack: LiveData<Boolean> = _navigationBack

    private val _bubbleMessageState = SingleLiveEvent<Int>()
    val bubbleMessageState: LiveData<Int> = _bubbleMessageState

    protected fun showBubbleMessage(message: Int) {
        _bubbleMessageState.value = message
    }

    protected fun back() {
        _navigationBack.value = true
    }

}