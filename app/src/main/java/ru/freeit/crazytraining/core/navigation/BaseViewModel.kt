package ru.freeit.crazytraining.core.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val _bubbleMessageState = MutableLiveData<Int>()
    val bubbleState: LiveData<Int> = _bubbleMessageState

    protected fun showBubbleMessage(message: Int) {
        _bubbleMessageState.value = message
        _bubbleMessageState.value = -1
    }

}